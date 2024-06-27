package org.slaega.family_secret.service;

import java.util.Optional;

import org.slaega.family_secret.mobel.MagicTokenModel;

public interface MagicLinkService {
    MagicTokenModel create(MagicTokenModel data);

    void deleteById(String id);

    Optional<MagicTokenModel> getById(String id);
}