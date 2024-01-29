package com.example.cowinstagram.follow.dto.request;

import com.example.cowinstagram.follow.domain.Follow;
import com.example.cowinstagram.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FollowRequest {

    private String followerUserId;
    private String followingUserId;

    public Follow toEntity(Member follower, Member following) {
        return Follow.builder()
                .follower(follower)
                .following(following)
                .build();
    }
}
