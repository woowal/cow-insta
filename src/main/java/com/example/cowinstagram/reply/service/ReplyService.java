package com.example.cowinstagram.reply.service;

import com.example.cowinstagram.comment.domain.Comment;
import com.example.cowinstagram.member.domain.Member;
import com.example.cowinstagram.reply.domain.Reply;
import com.example.cowinstagram.reply.dto.request.ReplyCreateRequest;
import com.example.cowinstagram.reply.dto.request.ReplyUpdateRequest;
import com.example.cowinstagram.reply.dto.response.ReplyResponse;
import com.example.cowinstagram.repository.CommentRepository;
import com.example.cowinstagram.repository.MemberRepository;
import com.example.cowinstagram.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void create(ReplyCreateRequest replyCreateRequest) {
        Member member = memberRepository.findById(replyCreateRequest.getMember()).get();
        Comment comment = commentRepository.findById(replyCreateRequest.getComment()).get();
        replyRepository.save(replyCreateRequest.toEntity(member, comment));
    }

    @Transactional
    public void update(Long id, ReplyUpdateRequest replyUpdateRequest) {
        Reply reply = replyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("답글이 존재하지 않습니다."));
        reply.update(replyUpdateRequest.getContent());
    }

    @Transactional(readOnly = true)
    public List<ReplyResponse> findAllByCommentId(Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        return replyRepository.findAllByComment(comment)
                .stream()
                .map(ReplyResponse::from)
                .toList();
    }

    @Transactional
    public void delete(Long id) {
        replyRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllByComments(List<Comment> comments) {
        replyRepository.deleteAllByCommentIn(comments);
    }

    @Transactional
    public void deleteAllByComment(Comment comment) {
        replyRepository.deleteAllByComment(comment);
    }

    @Transactional
    public void deleteAllByMember(Member member) {
        replyRepository.deleteAllByMember(member);
    }
}
