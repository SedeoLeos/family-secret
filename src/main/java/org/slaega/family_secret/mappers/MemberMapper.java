package org.slaega.family_secret.mappers;

import org.mapstruct.Mapper;
import org.slaega.family_secret.dto.member.MemberDto;
import org.slaega.family_secret.dto.member.RequestMemberDto;
import org.slaega.family_secret.mobel.Member;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member toEntity(MemberDto dto);
    MemberDto toDto(Member entity);
    MemberDto requestToResponse(RequestMemberDto requestMemberDto);
}
