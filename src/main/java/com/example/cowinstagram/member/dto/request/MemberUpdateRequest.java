package com.example.cowinstagram.member.dto.request;

import lombok.Getter;

@Getter
public class MemberUpdateRequest {

    private String userId;
    private String name;
    private String password;
    private String imageUrl;
}
