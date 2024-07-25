package org.slaega.family_secret.mappers;

import org.mapstruct.Mapper;

import org.slaega.family_secret.dto.discussion.DiscussionDto;
import org.slaega.family_secret.dto.discussion.RequestDiscussionDto;
import org.slaega.family_secret.mobel.Discussion;

@Mapper(componentModel = "spring")
public interface DiscussionMapper {
  
  DiscussionDto toDto (Discussion entity);
  Discussion toEntity (RequestDiscussionDto dto);
}
