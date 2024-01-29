package com.example.cowinstagram.auth.controller;

import com.example.cowinstagram.auth.JwtToken;
import com.example.cowinstagram.auth.dto.request.AuthLogInRequest;
import com.example.cowinstagram.auth.dto.request.AuthRegisterRequest;
import com.example.cowinstagram.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> register(@RequestPart AuthRegisterRequest authRegisterRequest,
                                         @Parameter(description = "multipart/form-data 형식의 이미지를 input으로 받는다.")
                                         @RequestPart(value = "multipartFile") MultipartFile multipartFile) throws IOException {
        authService.register(authRegisterRequest, multipartFile);

        return ResponseEntity.ok().build();
    }
}
