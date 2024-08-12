package org.slaega.family_secret.passwordless.service.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Data;


import org.slaega.family_secret.exception.ApiExceptionHandler;
import org.slaega.family_secret.passwordless.config.JwtConfig;

import org.slaega.family_secret.passwordless.dto.VerifyMagicLinkRequest;
import org.slaega.family_secret.passwordless.model.AuthUser;
import org.slaega.family_secret.passwordless.model.MagicLink;
import org.slaega.family_secret.passwordless.repository.AuthUserRepository;
import org.slaega.family_secret.passwordless.repository.MagicLinkRepository;
import org.slaega.family_secret.passwordless.repository.OneTimePasswordRepository;
import org.slaega.family_secret.passwordless.service.IMagicLinkService;
import org.slaega.family_secret.passwordless.util.Action;
import org.slaega.family_secret.passwordless.util.AuthErrors;
import org.slaega.family_secret.passwordless.util.JwtFactoryMagicLink;

import org.slaega.family_secret.passwordless.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Data
public class MagicLinkService implements IMagicLinkService {

    private final MagicLinkRepository magicLinkRepository;
    private final AuthUserRepository authUserRepository;
    private final OneTimePasswordRepository oneTimePasswordRepository;
    private final JwtFactoryMagicLink jwtFactoryMagicLink;


    @Autowired
    public MagicLinkService(
            MagicLinkRepository magicLinkRepository,
            AuthUserRepository authUserRepository,
            OneTimePasswordRepository oneTimePasswordRepository,
            JwtFactoryMagicLink jwtFactoryMagicLink) {
        this.magicLinkRepository = magicLinkRepository;
        this.authUserRepository = authUserRepository;
        this.oneTimePasswordRepository = oneTimePasswordRepository;
        this.jwtFactoryMagicLink = jwtFactoryMagicLink;

    }

    @Override
    public String create(Action action, AuthUser authUser) {
        JwtUtil jwtUtil = jwtFactoryMagicLink.getJwtUtil(action);
        int deleteCount = magicLinkRepository.deleteByAuthIdAndAction(authUser.getId(), action);
        MagicLink magicLink = new MagicLink();
        magicLink.setToken(NanoIdUtils.randomNanoId());
        magicLink.setAuth(authUser);
        magicLink.setAction(action);
        magicLink.setExpiresAt(new Date(System.currentTimeMillis() + jwtUtil.getTokenExpiration()));
        magicLink = this.magicLinkRepository.save(magicLink);
        System.out.println(deleteCount);
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("auth_Id", magicLink.getAuth().getId());

        System.out.println("count " + deleteCount);
        String jwt = jwtUtil.generateToken(extraClaims, magicLink.getToken());
        System.out.println(deleteCount);
        return jwt;
    }

    @Override
    public AuthUser verifyAccount(VerifyMagicLinkRequest request) throws ApiExceptionHandler {
        AuthUser authUser = verifyMagicLink(request.getToken(), Action.EMAIL_VERIFICATION);
        authUser.setVerified(true);
        return authUserRepository.save(authUser);
    }


    @Override
    public AuthUser verifyLogin(VerifyMagicLinkRequest request) throws ApiExceptionHandler {
        return verifyMagicLink(request.getToken(), Action.LOGIN);
    }


    private AuthUser verifyMagicLink(String token, Action action) throws ApiExceptionHandler {
        JwtUtil jwtUtil = jwtFactoryMagicLink.getJwtUtil(action);
        try {
            if (jwtUtil.isTokenExpired(token)) {
                throw new ApiExceptionHandler(List.of(AuthErrors.TOKEN_EXPIRED), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            String magicToken = jwtUtil.getSubject(token);
            UUID authId = jwtUtil.extractClaim(token, claims -> UUID.fromString(claims.get("auth_id", String.class)));

            MagicLink magicLink = magicLinkRepository.findFirstByAuthIdAndTokenAndAction(authId, magicToken, action).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

            magicLinkRepository.deleteByAuthIdAndAction(authId, action);
            oneTimePasswordRepository.deleteByAuthIdAndAction(authId, action);

            if (magicLink.getExpiresAt().before(new Date())) {
                throw new ApiExceptionHandler(List.of(AuthErrors.TOKEN_EXPIRED), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            AuthUser authUser = authUserRepository.findById(magicLink.getAuth().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
            if (action == Action.EMAIL_VERIFICATION && authUser.getVerified()) {
                throw new ApiExceptionHandler(List.of(AuthErrors.USER_ALREADY_VERIFIED), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            return authUser;

        } catch (Exception e) {
            throw new ApiExceptionHandler(List.of(AuthErrors.TOKEN_EXPIRED), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


}
