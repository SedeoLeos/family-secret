package org.slaega.family_secret.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import org.slaega.family_secret.dto.message.MessageDto;
import org.slaega.family_secret.dto.message.RequestMessageDto;
import org.slaega.family_secret.mobel.MessageModel;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "content", source = "dto.content")
    MessageModel toEntity(MessageDto dto);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "content", source = "dto.content")
    MessageDto toDto(MessageModel dto);


    // @Mapping(target = "id", source = "requestMessageDto.id")
    @Mapping(target = "content", source = "requestMessageDto.content")
    MessageDto postToRequest(RequestMessageDto requestMessageDto);

    
}
