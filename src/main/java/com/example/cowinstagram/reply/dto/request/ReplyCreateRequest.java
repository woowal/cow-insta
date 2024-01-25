package com.example.cowinstagram.reply.dto.request;

import com.example.cowinstagram.comment.domain.Comment;
import com.example.cowinstagram.member.domain.Member;
import com.example.cowinstagram.reply.domain.Reply;
import lombok.Getter;

@Getter
public class ReplyCreateRequest {

    private Long member;
    private Long comment;
    private String content;

    public Reply toEntity(Member member, Comment comment) {
        return Reply.builder()
                .member(member)
                .comment(comment)
                .content(this.content)
                .build();
    }
}
