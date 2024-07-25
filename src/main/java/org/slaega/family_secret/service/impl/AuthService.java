package org.slaega.family_secret.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slaega.family_secret.config.JwtConfig;
import org.slaega.family_secret.dto.auth.AuthenticationResponse;
import org.slaega.family_secret.dto.auth.SignInRequest;
import org.slaega.family_secret.dto.auth.SignUpRequest;
import org.slaega.family_secret.dto.auth.VerifyAccountByMagicLinkRequest;
import org.slaega.family_secret.dto.auth.VerifyAccountByOtpRequest;
import org.slaega.family_secret.mappers.UserMapper;
import org.slaega.family_secret.mobel.MagicLink;
import org.slaega.family_secret.mobel.User;
import org.slaega.family_secret.repository.UserRepository;
import org.slaega.family_secret.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final JwtConfig jwtConfig;

    private final UserRepository userRepository;
    private final OneTimePasswordService oneTimePasswordService;
    private final MagicLinkService magicLinkService;
    private final UserMapper userMapper;

    @Autowired
    public AuthService(UserRepository userRepository,
            MagicLinkService magicLinkService,
            OneTimePasswordService oneTimePasswordService,
            JwtConfig jwtConfig,
            UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.magicLinkService = magicLinkService;
        this.oneTimePasswordService = oneTimePasswordService;
        this.jwtConfig = jwtConfig;

    }

    public void signUp(SignUpRequest signUpRequest){
        Optional<User> existingUser = userRepository.findByEmail(signUpRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }

        User user = userMapper.toEntity(signUpRequest);
        user = userRepository.save(user);
        String action = "email_verification";
        System.out.println(oneTimePasswordService.create(action, user).getCode());
        System.out.println(magicLinkService.create(action, user));

        //mailService.sendSignupEmail(user.getEmail(), otp, magicLinkToken, user.getFirstname());
    }

    public void signIn(SignInRequest signInRequest)  {
        User user = new User();
        user.setEmail(signInRequest.getEmail());
        Optional<User> existingUser = userRepository.findOne(Example.of(user));
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }

        user = userMapper.toEntity(signInRequest);
        userRepository.save(user);
        String action = "email_verification";
        oneTimePasswordService.create(action, user).getCode();
        magicLinkService.create(action, user);

        //mailService.sendSignupEmail(user.getEmail(), otp, magicLinkToken, user.getFirstname());
    }

    public AuthenticationResponse verifyAccountByOTP(VerifyAccountByOtpRequest request) throws ResponseStatusException {
        User user = verifyOTP(request.getCode(), "email_verification", request.getEmail());
        user.setVerified(true);
        userRepository.save(user);

        return generateAuthenticationTokens(user);
    }

    // Other methods for login, loginByOTP, loginByMagicLink, etc.
    private User verifyOTP(String code, String action, String email) throws ResponseStatusException {
        User user = new User();
        user.setEmail(email);
        user = userRepository.findOne(Example.of(user)).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));
        oneTimePasswordService.findByUserIdAndCodeAndAction(user, code, action);

        oneTimePasswordService.deleteAllByUserIdAndAction(user, action);
        return user;
    }

    public AuthenticationResponse verifyAccountByMagicLink(VerifyAccountByMagicLinkRequest request) throws ResponseStatusException {
        User user = verifyMagicLink(request.getToken(), "email_verification");
        user.setVerified(true);
        userRepository.save(user);

        return generateAuthenticationTokens(user);
    }

    private User verifyMagicLink(String token, String action) throws ResponseStatusException {
        MagicLink magicLink = magicLinkService.findByTokenAndAction(token, action);

        User user = userRepository.findById(magicLink.getUser().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));

        magicLinkService.deleteAllByUserIdAndAction(action, user);
        return user;
    }

    private AuthenticationResponse generateAuthenticationTokens(User user) {
        String accessToken = createAccessToken(user.getId().toString(), user.getRole());
        String refreshToken = createRefreshToken(user.getId().toString(), user.getId().toString());

        AuthenticationResponse response = new AuthenticationResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setAccessTokenExpiresAt(LocalDateTime.now().plusMinutes(15));
        response.setRefreshTokenExpiresAt(LocalDateTime.now().plusDays(30));

        return response;
    }

    private String createAccessToken(String userId, String role) {
        JwtUtil jwtUtil = new JwtUtil(jwtConfig.getAccessSecret(), jwtConfig.getAccessExpired());
        return jwtUtil.generateToken(userId);

    }

    private String createRefreshToken(String refreshId, String userId) {
        JwtUtil jwtUtil = new JwtUtil(jwtConfig.getAccessSecret(), jwtConfig.getAccessExpired());
        return jwtUtil.generateToken(userId);

    }
}
