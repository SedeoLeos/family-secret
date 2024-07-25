package org.slaega.family_secret.repository;

import java.util.Optional;
import java.util.UUID;

import org.slaega.family_secret.mobel.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,UUID>{
    Optional<User> findByEmail(String email);
}
