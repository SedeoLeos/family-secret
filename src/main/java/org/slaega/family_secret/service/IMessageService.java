package org.slaega.family_secret.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slaega.family_secret.dto.message.MessageDto;
import org.slaega.family_secret.dto.message.RequestMessageDto;

public interface IMessageService {
    MessageDto create(RequestMessageDto message);
    Optional<MessageDto> findOne(UUID id);
    List<MessageDto> find();
    MessageDto update(UUID id, RequestMessageDto message);
    void deleteById(UUID id);
    
} 
