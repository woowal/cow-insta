package com.example.cowinstagram.member.dto.response;

import com.example.cowinstagram.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponse {

    private Long id;
    private String name;
    private String imageUrl;

    public MemberResponse(Long id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getName(), member.getImageUrl());
    }
}
