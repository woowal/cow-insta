package com.example.cowinstagram.repository;

import com.example.cowinstagram.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
