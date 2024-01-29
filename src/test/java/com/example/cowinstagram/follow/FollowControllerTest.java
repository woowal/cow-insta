package com.example.cowinstagram.follow;

import com.example.cowinstagram.follow.controller.FollowController;
import com.example.cowinstagram.follow.dto.request.FollowRequest;
import com.example.cowinstagram.follow.dto.response.FollowResponse;
import com.example.cowinstagram.follow.service.FollowService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(MockitoExtension.class)
public class FollowControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FollowService followService;

    @InjectMocks
    private FollowController followController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(followController).build();
    }

    @Test
    @DisplayName("사용자는 팔로우 목록 조회에 성공한다")
    public void findAllFollowersByUserIdSuccess() throws Exception {
        String userId = "userId";
        List<FollowResponse> followers = new ArrayList<>();
        followers.add(createFollowResponse("userId1"));
        followers.add(createFollowResponse("userId2"));

        when(followService.findAllFollowersByUserId(userId)).thenReturn(followers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/follows/follower/{userId}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value("userId1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userId").value("userId2"));
    }

    @Test
    @DisplayName("사용자는 팔로잉 목록 조회에 성공한다")
    public void findAllFollowingsByUserIdSuccess() throws Exception {
        String userId = "userId";
        List<FollowResponse> followings = new ArrayList<>();
        followings.add(createFollowResponse("userId1"));
        followings.add(createFollowResponse("userId2"));

        when(followService.findAllFollowingsByUserId(userId)).thenReturn(followings);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/follows/following/{userId}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value("userId1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userId").value("userId2"));
    }

    @Test
    @DisplayName("사용자는 팔로우에 성공한다")
    public void followSuccess() throws Exception {
        FollowRequest followRequest = createFollowRequest();
        String requestJson = asJsonString(followRequest);

        doNothing().when(followService).follow(any(FollowRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/follows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("사용자는 팔로우 취소에 성공한다")
    public void cancelFollowSuccess() throws Exception {
        FollowRequest followRequest = createFollowRequest();
        String requestJson = asJsonString(followRequest);

        doNothing().when(followService).cancelFollow(any(FollowRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/follows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private FollowResponse createFollowResponse(String userId) {
        return FollowResponse.builder()
                .userId(userId)
                .imageUrl("imageurl")
                .build();
    }

    private FollowRequest createFollowRequest() {
        return FollowRequest.builder()
                .followingUserId("followingUserId")
                .followerUserId("followerUserId")
                .build();
    }
}
