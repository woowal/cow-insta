package com.example.cowinstagram.comment.dto.response;

import com.example.cowinstagram.comment.domain.Comment;
import com.example.cowinstagram.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentResponse {

    private Long memberId;
    private String userId;
    private String content;

    public CommentResponse(Long memberId, String userId, String content) {
        this.memberId = memberId;
        this.userId = userId;
        this.content = content;
    }

    public static CommentResponse from(Comment comment) {
        Member member = comment.getMember();
        return new CommentResponse(member.getId(), member.getUserId(), comment.getContent());
    }
}
