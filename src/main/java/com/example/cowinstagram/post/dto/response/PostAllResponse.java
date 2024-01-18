package com.example.cowinstagram.post.dto.response;

import com.example.cowinstagram.post.domain.Post;
import lombok.Getter;

@Getter
public class PostAllResponse {

    private Long id;
    private String imageUrl;
    private String content;

    public PostAllResponse(Long id, String imageUrl, String content) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.content = content;
    }

    public static PostAllResponse from(Post post) {
        return new PostAllResponse(post.getId(), post.getImageUrl(), post.getContent());
    }
}
