package org.slaega.family_secret.service;

import java.util.Optional;

import org.slaega.family_secret.mobel.OneTimePasswordModel;

public interface OneTimePasswordService {
    OneTimePasswordModel create(OneTimePasswordModel data);

    void deleteById(String id);

    Optional<OneTimePasswordModel> getById(String id);
}
