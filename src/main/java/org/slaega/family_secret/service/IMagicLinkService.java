package org.slaega.family_secret.service;

import java.util.Optional;

import org.slaega.family_secret.mobel.MagicLinkModel;
import org.slaega.family_secret.mobel.UserModel;

public interface IMagicLinkService {
    MagicLinkModel create(String action, UserModel user);

    void deleteById(String id);

    Optional<MagicLinkModel> getById(String id);
}