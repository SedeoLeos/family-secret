package org.slaega.family_secret.passwordless.repository;
import org.slaega.family_secret.passwordless.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface AuthUserRepository extends JpaRepository<AuthUser,UUID>{
    Optional<AuthUser> findByEmail(String email);
}
