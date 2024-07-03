package org.slaega.family_secret.mappers;

import org.mapstruct.Mapper;

import org.slaega.family_secret.dto.discussion.DiscussionDto;
import org.slaega.family_secret.dto.discussion.RequestDiscussionDto;
import org.slaega.family_secret.mobel.DiscussionModel;

@Mapper(componentModel = "spring")
public interface DiscussionMapper {
  
  DiscussionDto toDto (DiscussionModel entity);
  DiscussionModel toEntity (RequestDiscussionDto dto);
}
