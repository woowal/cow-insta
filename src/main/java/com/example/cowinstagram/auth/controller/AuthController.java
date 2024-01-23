package com.example.cowinstagram.auth.controller;

import com.example.cowinstagram.auth.JwtToken;
import com.example.cowinstagram.auth.dto.request.AuthLogInRequest;
import com.example.cowinstagram.auth.dto.request.AuthRegisterRequest;
import com.example.cowinstagram.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/logIn")
    public ResponseEntity<JwtToken> logIn(@RequestBody AuthLogInRequest logInRequest) {
        JwtToken token = authService.logIn(logInRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody AuthRegisterRequest authRegisterRequest) {

        authService.register(authRegisterRequest);

        return ResponseEntity.ok().build();
    }
}
