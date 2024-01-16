package com.example.cowinstagram.member.dto.response;

import com.example.cowinstagram.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponse {

    private Long id;
    private String name;

    public MemberResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getName());
    }
}
