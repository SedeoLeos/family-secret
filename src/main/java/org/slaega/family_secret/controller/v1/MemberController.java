package org.slaega.family_secret.controller.v1;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slaega.family_secret.dto.member.MemberDto;
import org.slaega.family_secret.dto.member.RequestMemberDto;
import org.slaega.family_secret.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/members")
public class MemberController {
    private final IMemberService memberService;
    
    @Autowired
    public MemberController(IMemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping
    public MemberDto create(@RequestBody RequestMemberDto requestMemberDto) {
        return this.memberService.create(requestMemberDto);
    }

    @GetMapping
    public List<MemberDto> find() {
        return this.memberService.find();
    }

    @GetMapping("{id}")
    public Optional<MemberDto> findOne(@PathVariable UUID id) {
        return  this.memberService.findOne(id);
    }

    @PutMapping("{id}")
    public String putMethodName(@PathVariable UUID id, @RequestBody String entity) {
        return entity;
    }

    @DeleteMapping("{id}")
    public void putMethodName(@PathVariable UUID id) {
         this.memberService.deleteById(id);
    }

}
