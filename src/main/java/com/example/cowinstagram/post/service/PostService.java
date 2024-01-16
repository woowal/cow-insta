package com.example.cowinstagram.post.service;

import com.example.cowinstagram.comment.domain.Comment;
import com.example.cowinstagram.member.domain.Member;
import com.example.cowinstagram.post.domain.Post;
import com.example.cowinstagram.post.dto.request.PostCreateRequest;
import com.example.cowinstagram.post.dto.request.PostUpdateRequest;
import com.example.cowinstagram.post.dto.response.PostAllResponse;
import com.example.cowinstagram.post.dto.response.PostResponse;
import com.example.cowinstagram.repository.CommentRepository;
import com.example.cowinstagram.repository.MemberRepository;
import com.example.cowinstagram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void create(PostCreateRequest postCreateRequest) {
        Member member = memberRepository.findById(postCreateRequest.getMember()).get();
        postRepository.save(postCreateRequest.toEntity(member));
    }

    @Transactional(readOnly = true)
    public PostResponse findOne(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));
        List<String> comments = commentRepository.findByPostID(id)
                .stream()
                .map(Comment::getContent)
                .collect(Collectors.toList());

        return PostResponse.from(post, post.getMember().getName(), comments);
    }

    @Transactional
    public void update(Long id, PostUpdateRequest postUpdateRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));
        post.update(postUpdateRequest.getTitle(), postUpdateRequest.getContent());
    }

    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PostAllResponse> findAll() {
        return postRepository.findAll().stream()
                .map(PostAllResponse::from)
                .collect(Collectors.toList());
    }
}
