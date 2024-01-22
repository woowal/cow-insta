package com.example.cowinstagram.member.domain;

import com.example.cowinstagram.member.dto.request.MemberUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String name;
    private String password;

    @Builder
    public Member(String userId, String name, String password) {
        this.userId = userId;
        this.name = name;
        this.password = password;
    }

    public void update(MemberUpdateRequest memberUpdateRequest) {
        this.userId = memberUpdateRequest.getUserId();
        this.name = memberUpdateRequest.getName();
        this.password = memberUpdateRequest.getPassword();
    }
}
