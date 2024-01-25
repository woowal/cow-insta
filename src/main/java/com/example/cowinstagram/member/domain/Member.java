package com.example.cowinstagram.member.domain;

import com.example.cowinstagram.follow.domain.Follow;
import com.example.cowinstagram.member.dto.request.MemberUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;
    private String name;
    private String password;
    private String imageUrl;

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
        this.imageUrl = memberUpdateRequest.getImageUrl();
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
