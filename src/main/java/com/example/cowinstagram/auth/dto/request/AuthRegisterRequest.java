package com.example.cowinstagram.auth.dto.request;

import com.example.cowinstagram.member.domain.Member;
import lombok.Getter;

@Getter
public class AuthRegisterRequest {

    private String userId;
    private String name;
    private String password;

    public Member toEntity() {
        return Member.builder()
                .userId(this.userId)
                .name(this.name)
                .password(this.password)
                .build();
    }
}
