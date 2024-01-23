package com.example.cowinstagram.member.controller;

import com.example.cowinstagram.member.dto.request.MemberUpdateRequest;
import com.example.cowinstagram.member.dto.response.MemberResponse;
import com.example.cowinstagram.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> findOne(@PathVariable("id") String id) {

        MemberResponse memberResponse = memberService.findOne(id);

        return ResponseEntity.ok(memberResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody MemberUpdateRequest memberUpdateRequest) {

        memberService.update(id, memberUpdateRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

        memberService.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List> findAll() {

        List<MemberResponse> memberResponses = memberService.findAll();

        return ResponseEntity.ok(memberResponses);
    }
}

