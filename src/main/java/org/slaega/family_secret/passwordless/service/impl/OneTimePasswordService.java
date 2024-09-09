package org.slaega.family_secret.passwordless.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.slaega.family_secret.exception.ApiExceptionHandler;
import org.slaega.family_secret.passwordless.config.OneTimePasswordFactoryExpire;
import org.slaega.family_secret.passwordless.dto.VerifyOTPRequest;
import org.slaega.family_secret.passwordless.model.AuthUser;
import org.slaega.family_secret.passwordless.model.OneTimePassword;
import org.slaega.family_secret.passwordless.repository.AuthUserRepository;
import org.slaega.family_secret.passwordless.repository.MagicLinkRepository;
import org.slaega.family_secret.passwordless.repository.OneTimePasswordRepository;
import org.slaega.family_secret.passwordless.service.IOneTimePasswordService;

import org.slaega.family_secret.passwordless.util.Action;
import org.slaega.family_secret.passwordless.util.AuthErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class OneTimePasswordService implements IOneTimePasswordService {
    private final OneTimePasswordRepository oneTimePasswordRepository;
    private final AuthUserRepository authUserRepository;
    private final MagicLinkRepository magicLinkRepository;
    private final OneTimePasswordFactoryExpire oneTimePasswordFactoryExpire;

    @Autowired
    public OneTimePasswordService(OneTimePasswordRepository oneTimePasswordRepository, AuthUserRepository authUserRepository, MagicLinkRepository magicLinkRepository, OneTimePasswordFactoryExpire oneTimePasswordFactoryExpire) {

        this.oneTimePasswordRepository = oneTimePasswordRepository;
        this.authUserRepository = authUserRepository;
        this.magicLinkRepository = magicLinkRepository;
        this.oneTimePasswordFactoryExpire = oneTimePasswordFactoryExpire;

    }

    @Override
    public OneTimePassword create(Action action, AuthUser authUser) {
        oneTimePasswordRepository.deleteByAuthIdAndAction(authUser.getId(), action);
        SecureRandom secureRandom = new SecureRandom();
        int code = secureRandom.nextInt(999999) + 100000;
        OneTimePassword oneTimePassword = new OneTimePassword();
        oneTimePassword.setAction(action);
        oneTimePassword.setCode(String.valueOf(code));
        oneTimePassword.setAuth(authUser);
        oneTimePassword.setExpiresAt(new Date(System.currentTimeMillis() + oneTimePasswordFactoryExpire.expiresIn(action)*1000));
        return this.oneTimePasswordRepository.save(oneTimePassword);
    }

    @Override
    public AuthUser verifyAccount(VerifyOTPRequest request) throws ApiExceptionHandler {
        AuthUser authUser = verifyOTP(request.getCode(), Action.EMAIL_VERIFICATION, request.getEmail());
        authUser.setVerified(true);
        return authUserRepository.save(authUser);

    }

    @Override
    public AuthUser verifyLogin(VerifyOTPRequest request) throws ApiExceptionHandler {
        return verifyOTP(request.getCode(), Action.LOGIN, request.getEmail());
    }

    private AuthUser verifyOTP(String code, Action action, String email) throws ApiExceptionHandler {
        AuthUser authUser = authUserRepository.findByEmail(email).orElseThrow(() -> new ApiExceptionHandler(List.of(AuthErrors.INVALID_TOKEN), HttpStatus.UNAUTHORIZED));
        log.info(oneTimePasswordRepository.findFirstByAuthId(authUser.getId()).toString());
        OneTimePassword otp = oneTimePasswordRepository.findFirstByAuthIdAndActionAndCode(authUser.getId(), action, code).orElseThrow(() -> new ApiExceptionHandler(List.of(AuthErrors.INVALID_CREDENTIALS), HttpStatus.UNAUTHORIZED));
        oneTimePasswordRepository.deleteByAuthIdAndAction(authUser.getId(), action);
        magicLinkRepository.deleteByAuthIdAndAction(authUser.getId(), action);

        if (otp.getExpiresAt().before(new Date())) {
            throw new ApiExceptionHandler(List.of(AuthErrors.TOKEN_EXPIRED), HttpStatus.UNAUTHORIZED);
        }

        if (action == Action.EMAIL_VERIFICATION && authUser.getVerified()) {
            throw new ApiExceptionHandler(List.of(AuthErrors.INVALID_CREDENTIALS), HttpStatus.UNAUTHORIZED);
        }

        return authUser;
    }

}
