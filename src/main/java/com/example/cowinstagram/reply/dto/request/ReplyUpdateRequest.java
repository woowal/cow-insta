package com.example.cowinstagram.reply.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReplyUpdateRequest {

    private Long id;
    private String content;
}
