package org.slaega.family_secret.repository;

import java.util.Optional;

import org.slaega.family_secret.mobel.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface UserRepository extends JpaRepository<UserModel,String>{
    Optional<User> findByEmail(String email);
}
