package com.example.cowinstagram.member;

import com.example.cowinstagram.member.controller.MemberController;
import com.example.cowinstagram.member.dto.response.MemberResponse;
import com.example.cowinstagram.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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
public class MemberControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    public void findByIdSuccess() throws Exception {
        MemberResponse memberResponse = createMemberResponse();

        when(memberService.findOne(Mockito.anyString())).thenReturn(memberResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/testUserId"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)); // JSON 응답 확인
    }

    @Test
    void deleteSuccess() throws Exception {
        // Given
        Long memberId = 1L;
        doNothing().when(memberService).delete(memberId);

        // When & Then
        mockMvc.perform(delete("/api/members/{id}", memberId))
                .andExpect(status().isOk());
    }

    @Test
    void findAllSuccess() throws Exception {
        MemberResponse mockResponse = createMemberResponse();
        when(memberService.findAll()).thenReturn(Collections.singletonList(mockResponse));

        mockMvc.perform(get("/api/members"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("someId"));
    }

    private MemberResponse createMemberResponse() {
        return MemberResponse.builder()
                .id(3L)
                .name("임우")
                .imageUrl("https://cowinsta.s3.ap-southeast-2.amazonaws.com/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-01-02%20161224.png")
                .build();
    }
}
