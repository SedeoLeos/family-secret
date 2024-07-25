package org.slaega.family_secret.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slaega.family_secret.dto.discussion.DiscussionDto;
import org.slaega.family_secret.dto.discussion.RequestDiscussionDto;
public interface IDiscussionService {
    DiscussionDto create(RequestDiscussionDto requestDiscussionDto);
    DiscussionDto update(UUID id, RequestDiscussionDto requestDiscussionDto);
    List<DiscussionDto> find();
    void deleteById(UUID id);
    Optional<DiscussionDto> findById(UUID id);
}
