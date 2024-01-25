package com.example.cowinstagram.post.controller;

import com.example.cowinstagram.post.dto.request.PostCreateRequest;
import com.example.cowinstagram.post.dto.request.PostUpdateRequest;
import com.example.cowinstagram.post.dto.response.PostResponse;
import com.example.cowinstagram.post.service.PostService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestPart PostCreateRequest postCreateRequest,
                                       @Parameter(description = "multipart/form-data 형식의 이미지를 input으로 받는다.")
                                       @RequestPart(value = "multipartFile") MultipartFile multipartFile) throws IOException {

        postService.create(postCreateRequest, multipartFile);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findOne(@PathVariable("id") Long id) {

        PostResponse postResponse = postService.findOne(id);

        return ResponseEntity.ok(postResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody PostUpdateRequest postUpdateRequest) {

        postService.update(id, postUpdateRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws MalformedURLException {

        postService.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List> findAllByMemberId(@PathVariable("memberId") Long memberId) {

        List<PostResponse> postResponses = postService.findAllByMemberId(memberId);

        return ResponseEntity.ok(postResponses);
    }

    @GetMapping("/feed/{userId}")
    public ResponseEntity<List> findAllByFollowings(@PathVariable("userId") String userId) {

        List<PostResponse> postResponses = postService.findAllByFollowings(userId);

        return ResponseEntity.ok(postResponses);
    }
}
