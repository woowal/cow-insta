package com.example.cowinstagram.comment.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentUpdateRequest {

    private Long id;
    private String content;

}
