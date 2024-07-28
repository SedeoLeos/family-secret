package org.slaega.family_secret.passwordless.repository;

import jakarta.transaction.Transactional;
import org.slaega.family_secret.passwordless.model.MagicLink;
import org.slaega.family_secret.passwordless.util.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MagicLinkRepository extends JpaRepository<MagicLink,UUID> {
    @Transactional
    int deleteByAuthIdAndAction(UUID id, Action action);
    Optional<MagicLink> findFirstByAuthIdAndTokenAndAction(UUID id,String token,Action action);
}