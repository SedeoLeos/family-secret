package org.slaega.family_secret.passwordless.repository;

import jakarta.transaction.Transactional;
import org.slaega.family_secret.passwordless.model.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RefreshRepository extends JpaRepository<Refresh,String> {
    @Transactional
    void deleteByToken(String token);
}
