package org.slaega.family_secret.controller.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slaega.family_secret.dto.member.MemberDto;
import org.slaega.family_secret.dto.member.RequestMemberDto;
import org.slaega.family_secret.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("v1/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping
    public MemberDto create(@RequestBody RequestMemberDto requestMemberDto) {
        return this.memberService.create(requestMemberDto);
    }

    @GetMapping
    public String find(@RequestParam String param) {
        return new String();
    }

    @GetMapping(":id")
    public String findOne(@RequestParam String param) {
        return new String();
    }

    @PutMapping("{id}")
    public String putMethodName(@PathVariable String id, @RequestBody String entity) {
        return entity;
    }

    @DeleteMapping
    public String putMethodName(@PathVariable String id) {
        return id;
    }

}
