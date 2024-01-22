package com.example.cowinstagram.auth.dto.request;

import lombok.Getter;

@Getter
public class AuthLogInRequest {

    private String userId;
    private String password;
}
