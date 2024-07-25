package org.slaega.family_secret.repository;

import java.util.UUID;

import org.slaega.family_secret.mobel.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,UUID> {
    
}
