package com.example.cowinstagram.post;

import com.example.cowinstagram.post.controller.PostController;
import com.example.cowinstagram.post.dto.response.PostResponse;
import com.example.cowinstagram.post.service.PostService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    public void findByIdSuccess() throws Exception {
        PostResponse postResponse = createPostResponse();

        when(postService.findOne(Mockito.anyLong())).thenReturn(postResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/{id}", postResponse.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(postResponse.getId()));
    }

    @Test
    public void deleteSuccess() throws Exception {
        long postId = 1L;

        doNothing().when(postService).delete(postId);

        mockMvc.perform(delete("/api/posts/{id}", postId))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAllByMemberId() throws Exception {
        PostResponse postResponse = createPostResponse();
        long memberId = 1L;
        // When
        when(postService.findAllByMemberId(memberId)).thenReturn(Collections.singletonList(postResponse));

        // Then
        mockMvc.perform(get("/api/posts/member/{memberId}", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void testFindAllByFollowings() throws Exception {
        PostResponse postResponse = createPostResponse();
        String userId = "testUserId";
        // When
        when(postService.findAllByFollowings(userId)).thenReturn(Collections.singletonList(postResponse));

        // Then
        mockMvc.perform(get("/api/posts/feed/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }

    private PostResponse createPostResponse() {
        return PostResponse.builder()
                .id(1L)
                .imageUrl("https://cowinsta.s3.ap-southeast-2.amazonaws.com/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-01-02%20161224.png")
                .userId("jijile")
                .content("content")
                .build();
    }
}
