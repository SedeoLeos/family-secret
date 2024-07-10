package org.slaega.family_secret.repository;

import org.slaega.family_secret.mobel.MagicLinkModel;
import org.slaega.family_secret.mobel.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MagicLinkRepository extends JpaRepository<MagicLinkModel,String> {
    @Transactional
    void deleteAllByUserIdAndAction(UserModel user,String action);
}
