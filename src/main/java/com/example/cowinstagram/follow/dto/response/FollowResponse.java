package com.example.cowinstagram.follow.dto.response;

import com.example.cowinstagram.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FollowResponse {

    private Long id;
    private String userId;
    private String imageUrl;

    public FollowResponse(Long id, String userId, String imageUrl) {
        this.id = id;
        this.userId = userId;
        this.imageUrl = imageUrl;
    }

    public static FollowResponse from(Member member) {
        return new FollowResponse(member.getId(), member.getUserId(), member.getImageUrl());
    }
}
