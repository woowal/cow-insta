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

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> findOne(@PathVariable("id") Long id) {

        CommentResponse commentResponse = commentService.findOne(id);

        return ResponseEntity.ok(commentResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody CommentUpdateRequest commentUpdateRequest) {

        commentService.update(id, commentUpdateRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

        commentService.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List> findAll() {

        List<CommentResponse> commentResponses = commentService.findAll();

        return ResponseEntity.ok(commentResponses);
    }
}

