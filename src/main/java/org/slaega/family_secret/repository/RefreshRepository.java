package org.slaega.family_secret.repository;

import java.util.UUID;

import org.slaega.family_secret.mobel.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshRepository extends JpaRepository<Refresh,UUID>{
    
}
