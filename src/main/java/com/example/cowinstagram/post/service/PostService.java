package com.example.cowinstagram.post.service;

import com.example.cowinstagram.amazonS3.AmazonS3Service;
import com.example.cowinstagram.comment.domain.Comment;
import com.example.cowinstagram.comment.service.CommentService;
import com.example.cowinstagram.member.domain.Member;
import com.example.cowinstagram.post.domain.Post;
import com.example.cowinstagram.post.dto.request.PostCreateRequest;
import com.example.cowinstagram.post.dto.request.PostUpdateRequest;
import com.example.cowinstagram.post.dto.response.PostResponse;
import com.example.cowinstagram.repository.MemberRepository;
import com.example.cowinstagram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
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
    public void update(Long id, PostUpdateRequest postUpdateRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));
        post.update(postUpdateRequest.getImageUrl(), postUpdateRequest.getContent());
    }

    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id).get();
        commentService.deleteAllByPost(post);
        postRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllByMember(Member member) {
        postRepository.deleteAllByMember(member);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> findAllByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        return postRepository.findAllByMember(member).stream()
                .map((post) -> PostResponse.from(post, member.getUserId()))
                .collect(Collectors.toList());
    }
}
