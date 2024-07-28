package org.slaega.family_secret.passwordless.service.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.slaega.family_secret.passwordless.config.JwtConfig;
import org.slaega.family_secret.passwordless.model.AuthUser;
import org.slaega.family_secret.passwordless.model.Refresh;
import org.slaega.family_secret.passwordless.repository.RefreshRepository;
import org.slaega.family_secret.passwordless.service.IRefreshTokenService;
import org.slaega.family_secret.passwordless.util.Action;
import org.slaega.family_secret.passwordless.util.JwtResponse;
import org.slaega.family_secret.passwordless.util.JwtUtil;

import org.slaega.family_secret.passwordless.util.JwtPayload;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RefreshTokenService implements IRefreshTokenService {


    private RefreshRepository refreshRepository;
    private final JwtConfig jwtConfig;
    private final JwtUtil jwtUtil;
    private final long expiresIn;

    public RefreshTokenService(RefreshRepository refreshRepository, JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        this.refreshRepository = refreshRepository;
        this.jwtUtil = jwtConfig.jwtUtil(Action.REFRESH);
        this.expiresIn = jwtConfig.getExpiresIn(Action.REFRESH);
    }

    @Override
    public JwtResponse create(AuthUser authUser) {
        Refresh refresh = new Refresh();
        refresh.setToken(NanoIdUtils.randomNanoId());
        refresh.setAuth(authUser);
        refresh.setExpiresAt(new Date(System.currentTimeMillis() + expiresIn));
        refresh = refreshRepository.save(refresh);
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("auth_id", refresh.getAuth().getId());
        String jwt = jwtUtil.generateToken(extraClaims, refresh.getToken());
        return new JwtResponse(jwt,expiresIn);

    }

    @Override
    public void deleteRefreshToken(String token) {
        this.refreshRepository.deleteByToken(token);
    }

    @Override
    public JwtPayload verifyRefresh(String token) {
        String originalToken = this.jwtUtil.getSubject(token);
        UUID authID = this.jwtUtil.extractClaim(token, claims -> claims.get("auth_id", UUID.class));
        long expiresIn = this.jwtUtil.getTokenExpiration();
        return new JwtPayload(originalToken, authID, expiresIn);
    }


}
