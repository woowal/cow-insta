package com.example.cowinstagram.member.controller;

import com.example.cowinstagram.member.dto.request.MemberUpdateRequest;
import com.example.cowinstagram.member.dto.response.MemberResponse;
import com.example.cowinstagram.member.service.MemberService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{userId}")
    public ResponseEntity<MemberResponse> findOne(@PathVariable("userId") String userId) {

        MemberResponse memberResponse = memberService.findOne(userId);

        return ResponseEntity.ok(memberResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody MemberUpdateRequest memberUpdateRequest,
                                       @Parameter(description = "multipart/form-data 형식의 이미지를 input으로 받는다.")
                                       @RequestPart(value = "multipartFile") MultipartFile multipartFile) throws IOException {

        memberService.update(id, memberUpdateRequest, multipartFile);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws MalformedURLException {

        memberService.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List> findAll() {

        List<MemberResponse> memberResponses = memberService.findAll();

        return ResponseEntity.ok(memberResponses);
    }
}

