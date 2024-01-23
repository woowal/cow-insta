package com.example.cowinstagram.auth.dto.request;

import com.example.cowinstagram.member.domain.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthRegisterRequest {

    @NotBlank
    private String userId;
    @NotBlank
    private String name;
    @NotBlank
    private String password;

    public Member toEntity() {
        return Member.builder()
                .userId(this.userId)
                .name(this.name)
                .password(this.password)
                .build();
    }
}
