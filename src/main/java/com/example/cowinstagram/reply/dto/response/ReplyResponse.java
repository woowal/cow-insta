package com.example.cowinstagram.reply.dto.response;

import com.example.cowinstagram.member.domain.Member;
import com.example.cowinstagram.reply.domain.Reply;
import lombok.Getter;

@Getter
public class ReplyResponse {

    private String userId;
    private String content;

    public ReplyResponse(String userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public static ReplyResponse from(Reply reply) {
        Member member = reply.getMember();
        return new ReplyResponse(member.getUserId(), reply.getContent());
    }
}
