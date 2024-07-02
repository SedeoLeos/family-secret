package org.slaega.family_secret.service.impl;

import java.util.List;
import java.util.Optional;

import org.slaega.family_secret.dto.discussion.DiscussionDto;
import org.slaega.family_secret.dto.message.MessageDto;
import org.slaega.family_secret.dto.message.RequestMessageDto;
import org.slaega.family_secret.mappers.MessageMapper;
import org.slaega.family_secret.mobel.MessageModel;
import org.slaega.family_secret.repository.MessageRepository;
import org.slaega.family_secret.service.DiscussionService;
import org.slaega.family_secret.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private DiscussionService discussionService;
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public MessageDto create(RequestMessageDto message) {

        Optional<DiscussionDto> discussionDto = this.discussionService.findById(message.getDiscussionId());
        MessageDto messageDto = messageMapper.postToRequest(message);
        messageDto.setDiscussionDto(discussionDto.get());
        MessageModel messageModel = messageMapper.toEntity(messageDto);
        return messageMapper.toDto(this.messageRepository.save(messageModel));
    }

    @Override
    public Optional<MessageDto> findOne(String id) {
        Optional<MessageModel> messageModel = this.messageRepository.findById(id);
        if (messageModel.isPresent()) {
            return Optional.of(messageMapper.toDto(messageModel.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<MessageDto> find() {
        return this.messageRepository.findAll().stream().map(e -> messageMapper.toDto(e)).toList();
    }

    @Override
    public MessageDto update(String id, RequestMessageDto message) {
        MessageModel messageModel = this.messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("invalid id"));
        MessageDto messageDto = messageMapper.postToRequest(message);

        MessageModel messageModel2 = messageMapper.toEntity(messageDto);
        messageModel2.setDiscussion(messageModel.getDiscussion());
        return messageMapper.toDto(this.messageRepository.save(messageModel2));

    }

    @Override
    public void deleteById(String id) {
        this.messageRepository.deleteById(id);
    }

}
