package com.example.cowinstagram.reply;

import com.example.cowinstagram.reply.controller.ReplyController;
import com.example.cowinstagram.reply.dto.request.ReplyCreateRequest;
import com.example.cowinstagram.reply.dto.request.ReplyUpdateRequest;
import com.example.cowinstagram.reply.dto.response.ReplyResponse;
import com.example.cowinstagram.reply.service.ReplyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MockitoExtension.class)
public class ReplyControllerTest {

    @Mock
    private ReplyService replyService;

    @InjectMocks
    private ReplyController replyController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(replyController).build();
    }

    private MockMvc mockMvc;

    @Test
    void testCreateReply() throws Exception {
        ReplyCreateRequest request = createReplyCreateRequest();

        doNothing().when(replyService).create(any(ReplyCreateRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/replies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateReply() throws Exception {
        ReplyUpdateRequest request = createReplyUpdateRequest();

        doNothing().when(replyService).update(any(ReplyUpdateRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/replies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteReply() throws Exception {
        long replyId = 1L;

        doNothing().when(replyService).delete(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/replies/{id}", replyId))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAllByCommentId() throws Exception {
        long commentId = 1L;
        ReplyResponse replyResponse = createReplyResponse();

        when(replyService.findAllByCommentId(commentId)).thenReturn(Collections.singletonList(replyResponse));


        mockMvc.perform(MockMvcRequestBuilders.get("/api/replies/{commentId}", commentId))
                .andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ReplyCreateRequest createReplyCreateRequest() {
        return ReplyCreateRequest.builder()
                .member(1L)
                .comment(1L)
                .content("내용")
                .build();
    }

    private ReplyUpdateRequest createReplyUpdateRequest() {
        return ReplyUpdateRequest.builder()
                .id(1L)
                .content("내용")
                .build();
    }

    private ReplyResponse createReplyResponse() {
        return ReplyResponse.builder()
                .userId("userid")
                .content("내용")
                .build();
    }
}
