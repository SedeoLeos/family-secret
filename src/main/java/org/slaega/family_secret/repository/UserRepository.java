package org.slaega.family_secret.repository;

import java.util.Optional;

import org.slaega.family_secret.mobel.UserMobel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface UserRepository extends JpaRepository<UserMobel,String>{
    Optional<User> findByEmail(String email);
}
