package org.slaega.family_secret.service.impl;

import java.util.List;
import java.util.Optional;

import org.slaega.family_secret.dto.discussion.DiscussionDto;
import org.slaega.family_secret.dto.discussion.RequestDiscussionDto;
import org.slaega.family_secret.mappers.DiscussionMapper;
import org.slaega.family_secret.mobel.DiscussionModel;
import org.slaega.family_secret.repository.DiscussionRepository;
import org.slaega.family_secret.service.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DiscussionServiceImpl implements DiscussionService {
    @Autowired
    private DiscussionRepository discussionRepository;
   @Autowired
    private DiscussionMapper discussionMapper;

    @Override
    public DiscussionDto create(RequestDiscussionDto requestDiscussionDto) {
        DiscussionDto discussionDto = this.discussionMapper.requestToDto(requestDiscussionDto);
        DiscussionModel discussionModel = this.discussionMapper.toEntity(discussionDto);
        return this.discussionMapper.toDto(this.discussionRepository.save(discussionModel));
    }

    @Override
    public DiscussionDto update(String id, RequestDiscussionDto requestDiscussionDto) {
        DiscussionDto discussionDto = this.discussionMapper.requestToDto(requestDiscussionDto);
        DiscussionModel discussionModel = this.discussionMapper.toEntity(discussionDto);
        return this.discussionMapper.toDto(this.discussionRepository.save(discussionModel));
    }

    @Override
    public List<DiscussionDto> find() {
        return this.discussionRepository.findAll().stream().map(d -> this.discussionMapper.toDto(d)).toList();
    }

    @Override
    public void deleteById(String id) {
        this.discussionRepository.deleteById(id);
    }

    @Override
    public Optional<DiscussionDto> findById(String id) {
        Optional<DiscussionModel> discussionModel = this.discussionRepository.findById(id);
        if (discussionModel.isPresent()) {
            return Optional.of(this.discussionMapper.toDto(discussionModel.get()));
        }
        return Optional.empty();

    }

}
