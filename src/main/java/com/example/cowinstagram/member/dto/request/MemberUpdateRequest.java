package com.example.cowinstagram.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberUpdateRequest {

    private String userId;
    private String name;
    private String password;
    private String imageUrl;

}
