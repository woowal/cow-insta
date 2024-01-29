package com.example.cowinstagram.comment.controller;

import com.example.cowinstagram.comment.dto.request.CommentCreateRequest;
import com.example.cowinstagram.comment.dto.request.CommentUpdateRequest;
import com.example.cowinstagram.comment.dto.response.CommentResponse;
import com.example.cowinstagram.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CommentCreateRequest commentCreateRequest) {

        commentService.create(commentCreateRequest);

        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Void> update(@RequestBody CommentUpdateRequest commentUpdateRequest) {

        commentService.update(commentUpdateRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

        commentService.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List> findAllByPostId(@PathVariable("postId") Long postId) {

        List<CommentResponse> commentResponses = commentService.findAllByPostId(postId);

        return ResponseEntity.ok(commentResponses);
    }
}

