package org.slaega.family_secret.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slaega.family_secret.dto.discussion.DiscussionDto;
import org.slaega.family_secret.dto.discussion.RequestDiscussionDto;
import org.slaega.family_secret.mappers.DiscussionMapper;
import org.slaega.family_secret.mobel.Discussion;
import org.slaega.family_secret.mobel.Member;
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
        List<Member> memberModels = this.memberRepository.findAllById(requestDiscussionDto.getMembersId());
        Discussion discussion = this.discussionMapper.toEntity(requestDiscussionDto);
        discussion.setMembers(memberModels);
        return this.discussionMapper.toDto(this.discussionRepository.save(discussion));
    }

    @Override
    public DiscussionDto update(UUID id, RequestDiscussionDto requestDiscussionDto) {
        Discussion discussionModel = this.discussionMapper.toEntity(requestDiscussionDto);
        return this.discussionMapper.toDto(this.discussionRepository.save(discussionModel));
    }

    @Override
    public List<DiscussionDto> find() {
        return this.discussionRepository.findAll().stream().map(d -> this.discussionMapper.toDto(d)).toList();
    }

    @Override
    public void deleteById(UUID id) {
        this.discussionRepository.deleteById(id);
    }

    @Override
    public Optional<DiscussionDto> findById(UUID id) {
        Optional<Discussion> discussion = this.discussionRepository.findById(id);
        if (discussion.isPresent()) {
            return Optional.of(this.discussionMapper.toDto(discussion.get()));
        }
        return Optional.empty();

    }
}
