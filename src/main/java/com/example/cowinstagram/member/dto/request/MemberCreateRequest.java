package com.example.cowinstagram.member.dto.request;

import com.example.cowinstagram.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberCreateRequest {

    private String name;

    public Member toEntity() {
        return Member.builder()
                .name(this.name)
                .build();
    }
}
