package org.slaega.family_secret.repository;

import java.util.UUID;

import org.slaega.family_secret.mobel.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussionRepository extends JpaRepository<Discussion,UUID>{
    
}
