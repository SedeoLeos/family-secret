package org.slaega.family_secret.repository;

import org.slaega.family_secret.mobel.OneTimePasswordModel;
import org.slaega.family_secret.mobel.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface OneTimePasswordRepository extends JpaRepository<OneTimePasswordModel,String> {
    @Transactional
    void deleteAllByUserIdAndAction(UserModel user,String action);
} 
 