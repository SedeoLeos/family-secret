package org.slaega.family_secret.passwordless.repository;

import jakarta.transaction.Transactional;
import org.slaega.family_secret.passwordless.model.OneTimePassword;
import org.slaega.family_secret.passwordless.util.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OneTimePasswordRepository extends JpaRepository<OneTimePassword,UUID> {
    @Transactional
    void deleteByAuthIdAndAction(UUID id,Action action);
    Optional<OneTimePassword> findFirstByAuthIdAndActionAndCode(UUID id, Action action, String code);
} 
 