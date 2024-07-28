package org.slaega.family_secret.passwordless.service;

import org.slaega.family_secret.passwordless.model.AuthUser;
import java.util.Optional;
import java.util.UUID;

public interface IAuthUserService {
    Optional<AuthUser> findById(UUID id);

    AuthUser save(AuthUser authUser);

    void deleteById(UUID id);
}
