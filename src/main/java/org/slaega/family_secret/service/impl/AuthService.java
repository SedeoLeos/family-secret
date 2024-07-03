package org.slaega.family_secret.service.impl;

import java.time.LocalDateTime;

import org.slaega.family_secret.dto.auth.AuthenticationResponse;
import org.slaega.family_secret.mobel.MagicLinkModel;
import org.slaega.family_secret.mobel.UserModel;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MailService mailService;

    public void signup(SignupRequest signupRequest) throws ConflictException {
        Optional<User> existingUser = userRepository.findByEmail(signupRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new ConflictException("Email already in use");
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setFirstname(signupRequest.getFirstname());
        user.setLastname(signupRequest.getLastname());
        userRepository.save(user);

        String otp = generateOTP("email_verification", user.getId());
        String magicLinkToken = generateMagicToken("email_verification", user.getId());

        mailService.sendSignupEmail(user.getEmail(), otp, magicLinkToken, user.getFirstname());
    }

    public AuthenticationResponse verifyAccountByOTP(VerifyAccountByOtpRequest request) throws UnauthorizedException {
        User user = verifyOTP(request.getCode(), "email_verification", request.getEmail());
        user.setVerified(true);
        userRepository.save(user);

        return generateAuthenticationTokens(user);
    }

    public AuthenticationResponse verifyAccountByMagicLink(VerifyAccountByMagicLinkRequest request) throws UnauthorizedException {
        User user = verifyMagicLink(request.getToken(), "email_verification");
        user.setVerified(true);
        userRepository.save(user);

        return generateAuthenticationTokens(user);
    }

    // Other methods for login, loginByOTP, loginByMagicLink, etc.

    private String generateOTP(String action, Long userId) {
        authRepository.deleteAllByUserIdAndAction(userId, action);

        OneTimePassword otp = new OneTimePassword();
        otp.setCode(String.valueOf(new Random().nextInt(999999)));
        otp.setUserId(userId);
        otp.setAction(action);
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        authRepository.save(otp);

        return otp.getCode();
    }

    private String generateMagicToken(String action, Long userId) {
        authRepository.deleteAllByUserIdAndAction(userId, action);

        MagicToken magicToken = new MagicToken();
        magicToken.setToken(UUID.randomUUID().toString());
        magicToken.setUserId(userId);
        magicToken.setAction(action);
        magicToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        authRepository.save(magicToken);

        return jwtService.createJwtToken(userId, magicToken.getToken(), action);
    }

    private User verifyOTP(String code, String action, String email) throws UnauthorizedException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UnauthorizedException("Invalid token"));
        OneTimePassword otp = authRepository.findByUserIdAndCodeAndAction(user.getId(), code, action)
                .orElseThrow(() -> new UnauthorizedException("Invalid token"));

        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new UnauthorizedException("Token expired");
        }

        authRepository.deleteAllByUserIdAndAction(user.getId(), action);
        return user;
    }

    private UserModel verifyMagicLink(String token, String action) throws UnauthorizedException {
        MagicLinkModel magicToken = authRepository.findByTokenAndAction(token, action)
                .orElseThrow(() -> new UnauthorizedException("Invalid token"));

        if (magicToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new UnauthorizedException("Token expired");
        }

        UserModel user = userRepository.findById(magicToken.getUserId())
                .orElseThrow(() -> new UnauthorizedException("Invalid token"));

        authRepository.deleteAllByUserIdAndAction(user.getId(), action);
        return user;
    }

    private AuthenticationResponse generateAuthenticationTokens(User user) {
        String accessToken = jwtService.createAccessToken(user.getId(), user.getRole());
        String refreshToken = jwtService.createRefreshToken(user.getId());

        AuthenticationResponse response = new AuthenticationResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setAccessTokenExpiresAt(LocalDateTime.now().plusMinutes(15));
        response.setRefreshTokenExpiresAt(LocalDateTime.now().plusDays(30));

        return response;
    }
}

