package org.slaega.family_secret.repository;

import org.slaega.family_secret.mobel.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<MessageModel,String>{
    
}
