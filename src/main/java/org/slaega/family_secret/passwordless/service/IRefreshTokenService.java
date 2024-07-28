package org.slaega.family_secret.passwordless.service;

import org.slaega.family_secret.passwordless.model.AuthUser;
import org.slaega.family_secret.passwordless.util.JwtPayload;
import org.slaega.family_secret.passwordless.util.JwtResponse;

public interface IRefreshTokenService {
    JwtResponse create(AuthUser authUser);
    void deleteRefreshToken(String token);
    JwtPayload verifyRefresh(String token);

}