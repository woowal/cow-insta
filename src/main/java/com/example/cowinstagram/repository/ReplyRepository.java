package com.example.cowinstagram.repository;

import com.example.cowinstagram.comment.domain.Comment;
import com.example.cowinstagram.member.domain.Member;
import com.example.cowinstagram.reply.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findAllByComment(Comment comment);
    void deleteAllByCommentIn(List<Comment> comments);
    void deleteAllByComment(Comment comment);
    void deleteAllByMember(Member member);
}
