package com.example.cowinstagram.post;

import com.example.cowinstagram.post.controller.PostController;
import com.example.cowinstagram.post.dto.response.PostResponse;
import com.example.cowinstagram.post.service.PostService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("사용자는 게시글 생성에 성공한다")
    public void findByIdSuccess() throws Exception {
        PostResponse postResponse = createPostResponse();

        when(postService.findOne(Mockito.anyLong())).thenReturn(postResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/{id}", postResponse.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(postResponse.getId()));
    }

    @Test
    @DisplayName("사용자는 게시글 삭제에 성공한다")
    public void deleteSuccess() throws Exception {
        long postId = 1L;

        doNothing().when(postService).delete(postId);

        mockMvc.perform(delete("/api/posts/{id}", postId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("사용자는 특정 사용자의 게시글 조회에 성공한다")
    void findAllByMemberIdSuccess() throws Exception {
        PostResponse postResponse = createPostResponse();
        long memberId = 1L;

        when(postService.findAllByMemberId(memberId)).thenReturn(Collections.singletonList(postResponse));

        mockMvc.perform(get("/api/posts/member/{memberId}", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    @DisplayName("사용자는 팔로잉한 사용자들의 게시글 조회에 성공한다")
    void findAllByFollowingsSuccess() throws Exception {
        PostResponse postResponse = createPostResponse();
        String userId = "userId";

        when(postService.findAllByFollowings(userId)).thenReturn(Collections.singletonList(postResponse));

        mockMvc.perform(get("/api/posts/feed/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }

    private PostResponse createPostResponse() {
        return PostResponse.builder()
                .id(1L)
                .imageUrl("imageurl")
                .userId("userid")
                .content("content")
                .build();
    }
}
