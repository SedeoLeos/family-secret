package org.slaega.family_secret.service;

import java.util.Optional;
import java.util.UUID;

import org.slaega.family_secret.mobel.MagicLink;
import org.slaega.family_secret.mobel.User;

public interface IMagicLinkService {
    String create(String action, User user);

    void deleteById(UUID id);

    Optional<MagicLink> getById(UUID id);
}