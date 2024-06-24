package org.slaega.family_secret.repository;

import org.slaega.family_secret.mobel.MemberModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberModel,String> {
    
}
