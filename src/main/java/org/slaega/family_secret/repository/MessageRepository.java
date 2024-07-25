package org.slaega.family_secret.repository;

import java.util.UUID;

import org.slaega.family_secret.mobel.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,UUID>{
    
}
