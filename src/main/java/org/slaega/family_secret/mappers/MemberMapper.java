package org.slaega.family_secret.mappers;

import org.mapstruct.Mapper;
import org.slaega.family_secret.dto.member.MemberDto;
import org.slaega.family_secret.dto.member.RequestMemberDto;
import org.slaega.family_secret.mobel.MemberModel;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberModel toEntity(MemberDto dto);
    MemberDto toDto(MemberModel entity);
    MemberDto requestToResponse(RequestMemberDto requestMemberDto);
}
