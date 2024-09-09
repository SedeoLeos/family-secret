package org.slaega.family_secret.passwordless.repository;

import jakarta.transaction.Transactional;
import org.slaega.family_secret.passwordless.model.OneTimePassword;
import org.slaega.family_secret.passwordless.util.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OneTimePasswordRepository extends JpaRepository<OneTimePassword,String> {
    @Transactional
    void deleteByAuthIdAndAction(String id,Action action);
    Optional<OneTimePassword> findFirstByAuthIdAndActionAndCode(String id, Action action, String code);
    Optional<OneTimePassword> findFirstByAuthId(String id);
}
 