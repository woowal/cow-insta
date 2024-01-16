package com.example.cowinstagram.post.dto.response;

import com.example.cowinstagram.post.domain.Post;
import lombok.Getter;

@Getter
public class PostAllResponse {

    private Long id;
    private String title;
    private String content;

    public PostAllResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static PostAllResponse from(Post post) {
        return new PostAllResponse(post.getId(), post.getTitle(), post.getContent());
    }
}
