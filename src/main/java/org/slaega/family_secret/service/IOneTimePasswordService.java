package org.slaega.family_secret.service;

import java.util.Optional;

import org.slaega.family_secret.mobel.OneTimePasswordModel;
import org.slaega.family_secret.mobel.UserModel;

public interface IOneTimePasswordService {
    OneTimePasswordModel create(String action,UserModel user);

    void deleteById(String id);

    Optional<OneTimePasswordModel> getById(String id);
}
