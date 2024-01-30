package com.example.cowinstagram.post.service;

import com.example.cowinstagram.amazonS3.AmazonS3Service;
import com.example.cowinstagram.comment.service.CommentService;
import com.example.cowinstagram.follow.domain.Follow;
import com.example.cowinstagram.member.domain.Member;
import com.example.cowinstagram.post.domain.Post;
import com.example.cowinstagram.post.dto.request.PostCreateRequest;
import com.example.cowinstagram.post.dto.request.PostUpdateRequest;
import com.example.cowinstagram.post.dto.response.PostResponse;
import com.example.cowinstagram.repository.FollowRepository;
import com.example.cowinstagram.repository.MemberRepository;
import com.example.cowinstagram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final CommentService commentService;
    private final AmazonS3Service amazonS3Service;

    @Transactional
    public void create(PostCreateRequest postCreateRequest, MultipartFile multipartFile) throws IOException {
        Member member = memberRepository.findById(postCreateRequest.getMember()).get();
        String imageUrl = amazonS3Service.saveFile(multipartFile);
        postRepository.save(postCreateRequest.toEntity(member, imageUrl));
    }

    @Transactional(readOnly = true)
    public PostResponse findOne(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));

        return PostResponse.from(post, post.getMember().getUserId());
    }

    @Transactional
    public void update(Long id, PostUpdateRequest postUpdateRequest, MultipartFile multipartFile) throws IOException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));
        amazonS3Service.deleteImage(post.getImageUrl());
        String imageUrl = amazonS3Service.saveFile(multipartFile);
        post.update(imageUrl, postUpdateRequest.getContent());
    }

    @Transactional
    public void delete(Long id) throws MalformedURLException {
        Post post = postRepository.findById(id).get();
        commentService.deleteAllByPost(post);
        postRepository.deleteById(id);
        amazonS3Service.deleteImage(post.getImageUrl());
    }

    @Transactional
    public void deleteAllByMember(Member member) {
        postRepository.findAllByMember(member)
                .stream()
                .map(Post::getImageUrl)
                .map((imageUrl) -> {
                    try {
                         amazonS3Service.deleteImage(imageUrl);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                });

        postRepository.deleteAllByMember(member);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> findAllByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        return postRepository.findAllByMember(member).stream()
                .map((post) -> PostResponse.from(post, member.getUserId()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> findAllByFollowings(String userId) {
        Member member = memberRepository.findByUserId(userId).get();
        List<Member> followings = followRepository.findAllByFollower(member)
                .stream()
                .map(Follow::getFollowing)
                .toList();

        return postRepository.findAllByMemberIn(followings)
                .stream()
                .map((post)-> PostResponse.from(post, post.getMember().getUserId()))
                .toList();
    }
}
