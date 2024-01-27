package com.example.cowinstagram.comment.service;


import com.example.cowinstagram.comment.domain.Comment;
import com.example.cowinstagram.comment.dto.request.CommentCreateRequest;
import com.example.cowinstagram.comment.dto.request.CommentUpdateRequest;
import com.example.cowinstagram.comment.dto.response.CommentResponse;
import com.example.cowinstagram.member.domain.Member;
import com.example.cowinstagram.post.domain.Post;
import com.example.cowinstagram.reply.service.ReplyService;
import com.example.cowinstagram.repository.CommentRepository;
import com.example.cowinstagram.repository.MemberRepository;
import com.example.cowinstagram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ReplyService replyService;

    @Transactional
    public void create(CommentCreateRequest commentCreateRequest) {
        Member member = memberRepository.findById(commentCreateRequest.getMember()).get();
        Post post = postRepository.findById(commentCreateRequest.getPost()).get();
        commentRepository.save(commentCreateRequest.toEntity(member, post));
    }

    @Transactional(readOnly = true)
    public CommentResponse findOne(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("댓글이 존재하지 않습니다."));

        return CommentResponse.from(comment);
    }

    @Transactional
    public void update(Long id, CommentUpdateRequest commentUpdateRequest) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("댓글이 존재하지 않습니다."));
        comment.update(commentUpdateRequest.getContent());
    }

    @Transactional
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).get();
        replyService.deleteAllByComment(comment);
        commentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findAllByPostId(Long postId) {
        Post post = postRepository.findById(postId).get();
        return commentRepository.findAllByPost(post)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }

    @Transactional
    public void deleteAllByPost(Post post) {
        List<Comment> comments = commentRepository.findAllByPost(post);
        replyService.deleteAllByComments(comments);
        commentRepository.deleteAllByPost(post);
    }

    @Transactional
    public void deleteAllByMember(Member member) {
        commentRepository.deleteAllByMember(member);
    }
}

