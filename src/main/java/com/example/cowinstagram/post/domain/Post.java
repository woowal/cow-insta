package com.example.cowinstagram.post.domain;

import com.example.cowinstagram.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member")
    private Member member;

    private String imageUrl;
    private String content;

    @Builder
    public Post(Member member, String imageUrl, String content) {
        this.member = member;
        this.imageUrl = imageUrl;
        this.content = content;
    }

    public void update(String imageUrl, String content) {
        this.imageUrl = imageUrl;
        this.content = content;
    }
}
