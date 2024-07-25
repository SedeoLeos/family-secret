package org.slaega.family_secret.service;

import java.util.Optional;
import java.util.UUID;

import org.slaega.family_secret.mobel.User;

public interface IUserService {
    Optional<User> findById(UUID id);

    User save(User user);

    void deleteById(UUID id);
}
