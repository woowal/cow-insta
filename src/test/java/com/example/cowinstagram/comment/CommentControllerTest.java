package com.example.cowinstagram.comment;

import com.example.cowinstagram.comment.controller.CommentController;
import com.example.cowinstagram.comment.dto.request.CommentCreateRequest;
import com.example.cowinstagram.comment.dto.request.CommentUpdateRequest;
import com.example.cowinstagram.comment.dto.response.CommentResponse;
import com.example.cowinstagram.comment.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    @DisplayName("사용자는 댓글 생성에 성공한다")
    void createSuccess() throws Exception {
        CommentCreateRequest commentCreateRequest = createCommentCreateRequest();

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(commentCreateRequest);

        doNothing().when(commentService).create(any(CommentCreateRequest.class));

        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("사용자는 댓글 수정에 성공한다")
    void updateSuccess() throws Exception {
        CommentUpdateRequest commentUpdateRequest = createUpdateRequest();

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(commentUpdateRequest);

        doNothing().when(commentService).update(any(CommentUpdateRequest.class));

        mockMvc.perform(patch("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("사용자는 댓글 삭제에 성공한다")
    void deleteSuccess() throws Exception {
        long commentId = 1L;

        doNothing().when(commentService).delete(anyLong());

        mockMvc.perform(delete("/api/comments/{id}", commentId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("사용자는 특정 게시글의 댓글 조회에 성공한다")
    void findAllByPostIdSuccess() throws Exception {
        long postId = 1L;
        CommentResponse commentResponse = createCommentResponse();

        when(commentService.findAllByPostId(postId)).thenReturn(Collections.singletonList(commentResponse));

        mockMvc.perform(get("/api/comments/{postId}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").exists());
    }

    private CommentCreateRequest createCommentCreateRequest() {
        return CommentCreateRequest.builder()
                .member(3L)
                .post(1L)
                .content("내용")
                .build();
    }

    private CommentUpdateRequest createUpdateRequest() {
        return CommentUpdateRequest.builder()
                .id(1L)
                .content("내용")
                .build();
    }

    private CommentResponse createCommentResponse() {
        return CommentResponse.builder()
                .memberId(1L)
                .userId("userid")
                .content("내용")
                .build();
    }
}

