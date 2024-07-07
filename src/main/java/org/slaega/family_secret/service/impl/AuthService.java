package org.slaega.family_secret.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;


import org.slaega.family_secret.dto.auth.AuthenticationResponse;
import org.slaega.family_secret.dto.auth.SignUpRequest;
import org.slaega.family_secret.dto.auth.VerifyAccountByMagicLinkRequest;
import org.slaega.family_secret.dto.auth.VerifyAccountByOtpRequest;
import org.slaega.family_secret.mappers.UserMapper;
import org.slaega.family_secret.mobel.MagicLinkModel;
import org.slaega.family_secret.mobel.UserModel;
import org.slaega.family_secret.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    private OneTimePasswordService oneTimePasswordService;
    private MagicLinkService magicLinkService;
    private UserMapper userMapper;

    public void signUp(SignUpRequest signupRequest) throws ResponseStatusException {
        UserModel user = new UserModel();
        user.setEmail(signupRequest.getEmail());
         Optional<UserModel> existingUser = userRepository.findOne(Example.of(user));
        if (existingUser.isPresent()) {
            throw new  ResponseStatusException(HttpStatus.CONFLICT,"Email already in use");
        }

        user = userMapper.toEntity(signupRequest);
        userRepository.save(user);
        String action = "email_verification";
        oneTimePasswordService.create(action, user).getCode();
        magicLinkService.create(action, user);

        //mailService.sendSignupEmail(user.getEmail(), otp, magicLinkToken, user.getFirstname());
    }

    public AuthenticationResponse verifyAccountByOTP(VerifyAccountByOtpRequest request) throws ResponseStatusException {
        UserModel user = verifyOTP(request.getCode(), "email_verification", request.getEmail());
        user.setVerified(true);
        userRepository.save(user);

        return generateAuthenticationTokens(user);
    }

    

    // Other methods for login, loginByOTP, loginByMagicLink, etc.
   
    private UserModel verifyOTP(String code, String action, String email) throws ResponseStatusException {
        UserModel user = new UserModel();
        user.setEmail(email);
        user = userRepository.findOne(Example.of(user)).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));
        oneTimePasswordService.findByUserIdAndCodeAndAction(user, code, action);

        //authRepository.deleteAllByUserIdAndAction(user.getId(), action);
        return user;
    }
    public AuthenticationResponse verifyAccountByMagicLink(VerifyAccountByMagicLinkRequest request) throws ResponseStatusException {
        UserModel user = verifyMagicLink(request.getToken(), "email_verification");
        user.setVerified(true);
        userRepository.save(user);

        return generateAuthenticationTokens(user);
    }
    //private String generateMagicToken(String action, UserModel user) {
        //authRepository.deleteAllByUserIdAndAction(userId, action);

      //  return magicLinkService.create(action, user).getToken();

        //return jwtService.createJwtToken(userId, magicToken.getToken(), action);
    //}


    private UserModel verifyMagicLink(String token, String action)throws ResponseStatusException {
        MagicLinkModel magicLinkModel = magicLinkService.findByTokenAndAction(token, action);

        UserModel user = userRepository.findById(magicLinkModel.getUser().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));

        magicLinkService.deleteAllByUserIdAndAction(action,user);
        return user;
    }

    private AuthenticationResponse generateAuthenticationTokens(UserModel user) {
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
