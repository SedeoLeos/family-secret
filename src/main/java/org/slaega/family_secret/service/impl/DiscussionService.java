package org.slaega.family_secret.service.impl;

import java.util.List;
import java.util.Optional;

import org.slaega.family_secret.dto.discussion.DiscussionDto;
import org.slaega.family_secret.dto.discussion.RequestDiscussionDto;
import org.slaega.family_secret.mappers.DiscussionMapper;
import org.slaega.family_secret.mobel.DiscussionModel;
import org.slaega.family_secret.mobel.MemberModel;
import org.slaega.family_secret.repository.DiscussionRepository;
import org.slaega.family_secret.repository.MemberRepository;
import org.slaega.family_secret.service.IDiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscussionService implements IDiscussionService {

    private final DiscussionRepository discussionRepository;
    private final DiscussionMapper discussionMapper;
    private final MemberRepository memberRepository;

    @Autowired
    public DiscussionService(DiscussionMapper discussionMapper, DiscussionRepository discussionRepository,
            MemberRepository memberRepository) {
        this.discussionMapper = discussionMapper;
        this.discussionRepository = discussionRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public DiscussionDto create(RequestDiscussionDto requestDiscussionDto) {
        List<MemberModel> memberModels = this.memberRepository.findAllById(requestDiscussionDto.getMembersId());
        DiscussionModel discussionModel = this.discussionMapper.toEntity(requestDiscussionDto);
        discussionModel.setMembers(memberModels);
        return this.discussionMapper.toDto(this.discussionRepository.save(discussionModel));
    }

    @Override
    public DiscussionDto update(String id, RequestDiscussionDto requestDiscussionDto) {
        DiscussionModel discussionModel = this.discussionMapper.toEntity(requestDiscussionDto);
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
