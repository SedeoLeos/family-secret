package org.slaega.family_secret.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slaega.family_secret.dto.member.MemberDto;
import org.slaega.family_secret.dto.member.RequestMemberDto;
import org.slaega.family_secret.mappers.MemberMapper;
import org.slaega.family_secret.mobel.Member;
import org.slaega.family_secret.repository.MemberRepository;
import org.slaega.family_secret.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements IMemberService {

    
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    
    @Autowired
    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberDto create(RequestMemberDto requestMemberDto) {
        MemberDto memberDto = this.memberMapper.requestToResponse(requestMemberDto);
        return this.memberMapper.toDto(this.memberRepository.save(this.memberMapper.toEntity(memberDto)));
    }

    @Override
    public Optional<MemberDto> findOne(UUID id) {
        Optional<Member> memberModel = this.memberRepository.findById(id);
        if (memberModel.isPresent()) {
            return Optional.of(this.memberMapper.toDto(memberModel.get()));
        }
        return Optional.empty();

    }

    @Override
    public List<MemberDto> find() {
        return this.memberRepository.findAll().stream().map(mm -> this.memberMapper.toDto(mm)).toList();
    }

    @Override
    public MemberDto update(UUID id, RequestMemberDto requestMemberDto) {
        Optional<Member> memberModel = this.memberRepository.findById(id);
        if (memberModel.isPresent()) {
            MemberDto memberDto = this.memberMapper.requestToResponse(requestMemberDto);
            Member memberModel2 = this.memberMapper.toEntity(memberDto);
            memberModel2 = this.memberRepository.save(memberModel2);
            return this.memberMapper.toDto(memberModel2);
        }
        return null;
    }

    @Override
    public void deleteById(UUID id) {
       this.memberRepository.deleteById(id);
    }

    

    

}
