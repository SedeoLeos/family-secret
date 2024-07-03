package org.slaega.family_secret.repository;

import org.slaega.family_secret.mobel.MagicLinkModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagicLinkRepository extends JpaRepository<MagicLinkModel,String> {
    
}
