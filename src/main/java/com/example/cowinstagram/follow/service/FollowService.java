package com.example.cowinstagram.follow.service;

import com.example.cowinstagram.follow.domain.Follow;
import com.example.cowinstagram.follow.dto.request.FollowRequest;
import com.example.cowinstagram.follow.dto.response.FollowResponse;
import com.example.cowinstagram.member.domain.Member;
import com.example.cowinstagram.repository.FollowRepository;
import com.example.cowinstagram.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<FollowResponse> findAllFollowingsByUserId(String userId) {
        Member member = memberRepository.findByUserId(userId).get();
        return followRepository.findAllByFollower(member)
                .stream()
                .map(Follow::getFollowing)
                .map(FollowResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FollowResponse> findAllFollowersByUserId(String userId) {
        Member member = memberRepository.findByUserId(userId).get();
        return followRepository.findAllByFollowing(member)
                .stream()
                .map(Follow::getFollower)
                .map(FollowResponse::from)
                .toList();
    }

    @Transactional
    public void follow(FollowRequest followRequest) {
        Member follower = memberRepository.findByUserId(followRequest.getFollowerUserId()).get();
        Member following = memberRepository.findByUserId(followRequest.getFollowingUserId()).get();
        followRepository.save(followRequest.toEntity(follower, following));
    }

    @Transactional
    public void cancelFollow(FollowRequest followRequest) {
        Member follower = memberRepository.findByUserId(followRequest.getFollowerUserId()).get();
        Member following = memberRepository.findByUserId(followRequest.getFollowingUserId()).get();
        followRepository.deleteByFollowerAndFollowing(follower, following);
    }

    @Transactional
    public void deleteFollowsByMember(Member member) {
        followRepository.deleteAllByFollower(member);
        followRepository.deleteAllByFollowing(member);
    }
}
