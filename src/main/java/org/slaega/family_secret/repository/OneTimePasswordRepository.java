package org.slaega.family_secret.repository;

import java.util.UUID;

import org.slaega.family_secret.mobel.OneTimePassword;
import org.slaega.family_secret.mobel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface OneTimePasswordRepository extends JpaRepository<OneTimePassword,UUID> {
    @Transactional
    void deleteAllByUserIdAndAction(User user,String action);
} 
 