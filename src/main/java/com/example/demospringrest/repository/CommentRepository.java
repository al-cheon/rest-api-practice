package com.example.demospringrest.repository;

import com.example.demospringrest.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);

    Comment findByPostIdAndId(Long postId, Long id);
}
