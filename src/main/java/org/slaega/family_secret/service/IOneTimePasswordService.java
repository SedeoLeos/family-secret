package org.slaega.family_secret.service;

import java.util.Optional;
import java.util.UUID;

import org.slaega.family_secret.mobel.OneTimePassword;
import org.slaega.family_secret.mobel.User;

public interface IOneTimePasswordService {
    OneTimePassword create(String action,User user);

    void deleteById(UUID id);

    Optional<OneTimePassword> getById(UUID id);
}
