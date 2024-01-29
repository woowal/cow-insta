package com.example.cowinstagram.comment.dto.request;

import com.example.cowinstagram.comment.domain.Comment;
import com.example.cowinstagram.member.domain.Member;
import com.example.cowinstagram.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentCreateRequest {

    private Long member;
    private Long post;
    private String content;

    public Comment toEntity(Member member, Post post) {
        return Comment.builder()
                .member(member)
                .post(post)
                .content(this.content)
                .build();
    }
}
