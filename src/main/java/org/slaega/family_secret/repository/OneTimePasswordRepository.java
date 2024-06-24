package org.slaega.family_secret.repository;

import org.slaega.family_secret.mobel.OneTimePasswordModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneTimePasswordRepository extends JpaRepository<OneTimePasswordModel,String> {
} 
