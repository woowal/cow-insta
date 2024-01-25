package com.example.cowinstagram.repository;

import com.example.cowinstagram.comment.domain.Comment;
import com.example.cowinstagram.member.domain.Member;
import com.example.cowinstagram.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);

    void deleteAllByMember(Member member);

    void deleteAllByPost(Post post);
}
