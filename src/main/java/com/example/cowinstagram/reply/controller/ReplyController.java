package com.example.cowinstagram.reply.controller;

import com.example.cowinstagram.reply.dto.request.ReplyCreateRequest;
import com.example.cowinstagram.reply.dto.request.ReplyUpdateRequest;
import com.example.cowinstagram.reply.dto.response.ReplyResponse;
import com.example.cowinstagram.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ReplyCreateRequest replyCreateRequest) {

        replyService.create(replyCreateRequest);

        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Void> update(@RequestBody ReplyUpdateRequest replyUpdateRequest) {

        replyService.update(replyUpdateRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

        replyService.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<List> findAllByCommentId(@PathVariable("commentId") Long commentId) {

        List<ReplyResponse> replyResponses = replyService.findAllByCommentId(commentId);

        return ResponseEntity.ok(replyResponses);
    }
}
