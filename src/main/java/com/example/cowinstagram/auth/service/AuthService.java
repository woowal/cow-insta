package com.example.cowinstagram.auth.service;

import com.example.cowinstagram.amazonS3.AmazonS3Service;
import com.example.cowinstagram.auth.JwtToken;
import com.example.cowinstagram.auth.TokenProvider;
import com.example.cowinstagram.auth.dto.request.AuthLogInRequest;
import com.example.cowinstagram.auth.dto.request.AuthRegisterRequest;
import com.example.cowinstagram.member.domain.Member;
import com.example.cowinstagram.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final BCryptPasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final AmazonS3Service amazonS3Service;
    private final TokenProvider tokenProvider;

    public JwtToken logIn(AuthLogInRequest authLogInRequest) {
        Member logInMember = memberRepository.findByUserId(authLogInRequest.getUserId())
                .filter(member -> matches(member.getPassword(), authLogInRequest.getPassword()))
                .orElseThrow(() -> new NoSuchElementException("로그인에 실패하였습니다."));
        String authentication = String.format("%s:%s", logInMember.getId(), null);
        JwtToken token = tokenProvider.generateToken(authentication);

        return token;
    }

    @Transactional
    public void register(AuthRegisterRequest authRegisterRequest, MultipartFile multipartFile) throws IOException {
        Member member = authRegisterRequest.toEntity();
        String imageUrl = amazonS3Service.saveFile(multipartFile);
        member.updateImageUrl(imageUrl);
        memberRepository.save(member);
    }

    private boolean matches(String a, String b) {
        System.out.println("a: " + a);
        System.out.println("b: " + b);
        if (a.matches(b)) {
            return true;
        }
        return false;
    }
}
