package org.slaega.family_secret.service;

import java.util.Optional;


import org.slaega.family_secret.mobel.RefreshModel;
import org.slaega.family_secret.mobel.UserModel;

public interface IRefreshTokenService {
    String create(UserModel user);

    void deleteById(String id);

    Optional<RefreshModel> getById(String id);
}