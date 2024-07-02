package org.slaega.family_secret.service;

import java.util.List;
import java.util.Optional;

import org.slaega.family_secret.dto.member.MemberDto;
import org.slaega.family_secret.dto.member.RequestMemberDto;

public interface MemberService {
    MemberDto create(RequestMemberDto requestMemberDto);
    Optional<MemberDto> findOne(String id);
    List<MemberDto> find();
    MemberDto update(String id,RequestMemberDto requestMemberDto);
    void deleteById(String id);
}
