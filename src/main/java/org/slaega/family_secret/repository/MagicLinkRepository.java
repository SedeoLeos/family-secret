package org.slaega.family_secret.repository;

import org.slaega.family_secret.mobel.MagicTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagicLinkRepository extends JpaRepository<MagicTokenModel,String> {
    
}
