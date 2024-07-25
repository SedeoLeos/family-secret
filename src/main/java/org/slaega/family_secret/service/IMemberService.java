package org.slaega.family_secret.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slaega.family_secret.dto.member.MemberDto;
import org.slaega.family_secret.dto.member.RequestMemberDto;

public interface IMemberService {
    MemberDto create(RequestMemberDto requestMemberDto);
    Optional<MemberDto> findOne(UUID id);
    List<MemberDto> find();
    MemberDto update(UUID id,RequestMemberDto requestMemberDto);
    void deleteById(UUID id);
   
}
