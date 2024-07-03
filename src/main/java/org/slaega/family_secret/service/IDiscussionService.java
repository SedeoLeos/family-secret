package org.slaega.family_secret.service;

import org.slaega.family_secret.dto.discussion.DiscussionDto;
import org.slaega.family_secret.dto.discussion.RequestDiscussionDto;

import java.util.List;
import java.util.Optional;
public interface IDiscussionService {
    DiscussionDto create(RequestDiscussionDto requestDiscussionDto);
    DiscussionDto update(String id, RequestDiscussionDto requestDiscussionDto);
    List<DiscussionDto> find();
    void deleteById(String id);
    Optional<DiscussionDto> findById(String id);
}
