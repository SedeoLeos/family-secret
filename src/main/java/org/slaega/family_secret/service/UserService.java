package org.slaega.family_secret.service;

import java.util.Optional;

import org.slaega.family_secret.mobel.UserModel;

public interface UserService {
    Optional<UserModel> findById(String id);

    UserModel save(UserModel user);

    void deleteById(String id);
}
