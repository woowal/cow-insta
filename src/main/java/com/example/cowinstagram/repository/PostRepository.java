package com.example.cowinstagram.repository;

import com.example.cowinstagram.member.domain.Member;
import com.example.cowinstagram.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByMember(Member member);
    void deleteAllByMember(Member member);
}
