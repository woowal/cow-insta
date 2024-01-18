package com.example.cowinstagram.post.dto.request;

import com.example.cowinstagram.member.domain.Member;
import com.example.cowinstagram.post.domain.Post;
import lombok.Getter;

@Getter
public class PostCreateRequest {

    private Long member;
    private String imageUrl;
    private String content;

    public Post toEntity(Member member) {
        return Post.builder()
                .member(member)
                .imageUrl(this.imageUrl)
                .content(this.content)
                .build();
    }
}
