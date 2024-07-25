package org.slaega.family_secret.service;

import java.util.Optional;
import java.util.UUID;

import org.slaega.family_secret.mobel.Refresh;
import org.slaega.family_secret.mobel.User;

public interface IRefreshTokenService {
    String create(User user);

    void deleteById(UUID id);

    Optional<Refresh> getById(UUID id);
}