package com.example.cowinstagram.follow.controller;

import com.example.cowinstagram.follow.dto.request.FollowRequest;
import com.example.cowinstagram.follow.dto.response.FollowResponse;
import com.example.cowinstagram.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @GetMapping("/follower/{userId}")
    public ResponseEntity<List> findAllFollowersByUserId(@PathVariable("userId") String userId) {

        List<FollowResponse> followers = followService.findAllFollowersByUserId(userId);

        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<List> findAllFollowingsByUserId(@PathVariable("userId") String userId) {

        List<FollowResponse> followings = followService.findAllFollowingsByUserId(userId);

        return ResponseEntity.ok(followings);
    }

    @PostMapping
    public ResponseEntity<Void> follow(@RequestBody FollowRequest followRequest) {
        followService.follow(followRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelFollow(@RequestBody FollowRequest followRequest) {

        followService.cancelFollow(followRequest);

        return ResponseEntity.ok().build();
    }
}
