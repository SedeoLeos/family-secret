package org.slaega.family_secret.repository;

import java.util.UUID;

import org.slaega.family_secret.mobel.MagicLink;
import org.slaega.family_secret.mobel.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagicLinkRepository extends JpaRepository<MagicLink,UUID> {
    //@Transactional
    void deleteAllByUserIdAndAction(User user,String action);
}
