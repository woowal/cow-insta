package com.example.cowinstagram.post.dto.response;

import com.example.cowinstagram.post.domain.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class PostResponse {

    private Long id;
    private String imageUrl;
    private String name;
    private String content;
    private List<String> comments;

    public PostResponse(Long id, String imageUrl, String name, String content, List<String> comments) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.content = content;
        this.comments = comments;
    }

    public static PostResponse from(Post post, String name, List<String> comments) {
        return new PostResponse(post.getId(), post.getImageUrl(), name, post.getContent(), comments);
    }

}
