package org.slaega.family_secret.service;

import org.slaega.family_secret.dto.message.RequestMessageDto;
import org.slaega.family_secret.dto.message.MessageDto;
import java.util.Optional;
import java.util.List;

public interface IMessageService {
    MessageDto create(RequestMessageDto message);
    Optional<MessageDto> findOne(String id);
    List<MessageDto> find();
    MessageDto update(String id, RequestMessageDto message);
    void deleteById(String id);
    
} 
