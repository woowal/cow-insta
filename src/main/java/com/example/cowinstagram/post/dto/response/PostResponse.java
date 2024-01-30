package com.example.cowinstagram.post.dto.response;

import com.example.cowinstagram.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PostResponse {

    private Long id;
    private String imageUrl;
    private String userId;
    private String content;

    public PostResponse(Long id, String imageUrl, String userId, String content) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.content = content;
    }

    public static PostResponse from(Post post, String userId) {
        return new PostResponse(post.getId(), post.getImageUrl(), userId, post.getContent());
    }

}
