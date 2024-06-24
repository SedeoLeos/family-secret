package org.slaega.family_secret.repository;

import org.slaega.family_secret.mobel.DiscussionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussionRepository extends JpaRepository<DiscussionModel,String>{
    
}
