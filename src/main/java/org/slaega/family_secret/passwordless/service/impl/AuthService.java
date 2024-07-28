package org.slaega.family_secret.passwordless.service.impl;

import org.slaega.family_secret.mobel.User;
import org.slaega.family_secret.passwordless.config.JwtConfig;
import org.slaega.family_secret.passwordless.dto.*;
import org.slaega.family_secret.passwordless.mappers.AuthMapper;
import org.slaega.family_secret.passwordless.model.AuthUser;
import org.slaega.family_secret.passwordless.repository.AuthUserRepository;
import org.slaega.family_secret.passwordless.util.Action;
import org.slaega.family_secret.passwordless.util.JwtPayload;
import org.slaega.family_secret.passwordless.util.JwtResponse;
import org.slaega.family_secret.passwordless.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.Optional;

@Service
public class AuthService {

    private final JwtConfig jwtConfig;

    private final AuthUserRepository authUserRepository;
    private final OneTimePasswordService oneTimePasswordService;
    private final MagicLinkService magicLinkService;
    private final RefreshTokenService refreshTokenService;
    private final AuthMapper authMapper;

    @Autowired
    public AuthService(AuthUserRepository authUserRepository, RefreshTokenService refreshTokenService, MagicLinkService magicLinkService, OneTimePasswordService oneTimePasswordService, JwtConfig jwtConfig, AuthMapper authMapper) {
        this.authMapper = authMapper;
        this.authUserRepository = authUserRepository;
        this.magicLinkService = magicLinkService;
        this.oneTimePasswordService = oneTimePasswordService;
        this.refreshTokenService = refreshTokenService;
        this.jwtConfig = jwtConfig;

    }

    public void signUp(SignUpRequest signUpRequest) {
        AuthUser existingAuthUser = authUserRepository.findByEmail(signUpRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use"));

        AuthUser authUser = authMapper.toEntity(signUpRequest);
        authUser = authUserRepository.save(authUser);
        System.out.println(oneTimePasswordService.create(Action.EMAIL_VERIFICATION, authUser).getCode());
        System.out.println(magicLinkService.create(Action.EMAIL_VERIFICATION, authUser));
        //mailService.sendSignupEmail(user.getEmail(), otp, magicLinkToken, user.getFirstname());
    }

    public AuthenticationResponse verifyAccountByOTP(VerifyOTPRequest request) throws ResponseStatusException {
        AuthUser authUser = oneTimePasswordService.verifyAccount(request);
        return generateAuthenticationTokens(authUser);
    }

    public AuthenticationResponse verifyAccountByMagicLink(VerifyMagicLinkRequest request) throws ResponseStatusException {
        AuthUser authUser = magicLinkService.verifyAccount(request);
        return generateAuthenticationTokens(authUser);
    }

    public void resendVerificationEmail(ResendVerificationEmailRequest resendVerificationEmailRequest) throws ResponseStatusException {
        AuthUser user = this.authUserRepository.findByEmail(resendVerificationEmailRequest.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, ""));
        if (user.getVerified()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "");
        }
        String code = oneTimePasswordService.create(Action.EMAIL_VERIFICATION, user).getCode();
        System.out.println("********* code " + code);
        String token = magicLinkService.create(Action.EMAIL_VERIFICATION, user);
        System.out.println("********* token " + token);

    }

    public void signIn(SignInRequest signInRequest) {
        Optional<AuthUser> existingUser = authUserRepository.findByEmail(signInRequest.getEmail());
        if (existingUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account don't  exist");
        }
        String code = oneTimePasswordService.create(Action.LOGIN, existingUser.get()).getCode();
        System.out.println("********* code " + code);
        String token = magicLinkService.create(Action.LOGIN, existingUser.get());
        System.out.println("********* token " + token);
        //mailService.sendSignupEmail(user.getEmail(), otp, magicLinkToken, user.getFirstname());
    }

    public AuthenticationResponse loginByOTP(VerifyOTPRequest request) throws ResponseStatusException {
        AuthUser authUser = oneTimePasswordService.verifyLogin(request);
        return generateAuthenticationTokens(authUser);
    }

    public AuthenticationResponse loginByMagicLink(VerifyMagicLinkRequest request) throws ResponseStatusException {
        AuthUser authUser = magicLinkService.verifyLogin(request);
        return generateAuthenticationTokens(authUser);
    }


    private AuthenticationResponse generateAuthenticationTokens(AuthUser authUser) {
        JwtResponse accessToken = createAccessToken(authUser.getId().toString(), authUser.getRole());
        JwtResponse refreshToken = refreshTokenService.create(authUser);
        AuthenticationResponse response = new AuthenticationResponse();
        response.setAccessToken(accessToken.jwt());
        response.setRefreshToken(refreshToken.jwt());
        response.setAccessTokenExpiresAt(accessToken.expiresIn());
        response.setRefreshTokenExpiresAt(refreshToken.expiresIn());
        return response;
    }

    private JwtResponse createAccessToken(String userId, String role) {
        JwtUtil jwtUtil = jwtConfig.jwtUtil(Action.ACCESS);

        String jwt = jwtUtil.generateToken(userId);
        long expiresIn = jwtConfig.getExpiresIn(Action.ACCESS);
        return new JwtResponse(jwt, expiresIn);

    }

    /**
     * Handles user logout.
     * Clears the authentication cookies.
     * @param {Request} req - The Express Request object.
     * @param {Response} res - The Express Response object.
     */
    public AuthenticationResponse refresh(String token) {
        JwtPayload jwtPayload = this.refreshTokenService.verifyRefresh(token);
        AuthUser authUser = this.authUserRepository
                .findById(jwtPayload.authId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        this.refreshTokenService.deleteRefreshToken(token);
        return generateAuthenticationTokens(authUser);
    }
    /**
     * Handles user logout.
     * Clears the authentication cookies.
     * @param {Request} req - The Express Request object.
     * @param {Response} res - The Express Response object.
     */
    public void signOut(SignOut signOut) {
        JwtPayload jwtPayload = this.refreshTokenService.verifyRefresh(signOut.getToken());
        this.refreshTokenService.deleteRefreshToken(jwtPayload.token());
    }


}
