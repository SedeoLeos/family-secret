package org.slaega.family_secret.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slaega.family_secret.dto.discussion.DiscussionDto;
import org.slaega.family_secret.dto.message.MessageDto;
import org.slaega.family_secret.dto.message.RequestMessageDto;
import org.slaega.family_secret.mappers.MessageMapper;
import org.slaega.family_secret.mobel.Message;
import org.slaega.family_secret.repository.MessageRepository;
import org.slaega.family_secret.service.IDiscussionService;
import org.slaega.family_secret.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MessageService implements IMessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private IDiscussionService discussionService;
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public MessageDto create(RequestMessageDto message) {
        DiscussionDto discussionDto = this.discussionService.findById(message.getDiscussionId()).orElseThrow(
                () -> new EntityNotFoundException(
                        "Discussion not found with id: " + message.getDiscussionId()));
        MessageDto messageDto = messageMapper.postToRequest(message);
        messageDto.setDiscussionDto(discussionDto);
        Message messageModel = messageMapper.toEntity(messageDto);
        return messageMapper.toDto(this.messageRepository.save(messageModel));
    }

    @Override
    public Optional<MessageDto> findOne(UUID id) {
        Optional<Message> message = this.messageRepository.findById(id);
        if (message.isPresent()) {
            return Optional.of(messageMapper.toDto(message.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<MessageDto> find() {
        return this.messageRepository.findAll().stream().map(e -> messageMapper.toDto(e)).toList();
    }

    @Override
    public MessageDto update(UUID id, RequestMessageDto message) {
        Message messageModel = this.messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("invalid id"));
        MessageDto messageDto = messageMapper.postToRequest(message);

        Message messageModel2 = messageMapper.toEntity(messageDto);
        messageModel2.setDiscussion(messageModel.getDiscussion());
        return messageMapper.toDto(this.messageRepository.save(messageModel2));

    }

    @Override
    public void deleteById(UUID id) {
        this.messageRepository.deleteById(id);
    }

}
