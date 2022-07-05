package com.example.demospringrest.service;

import com.example.demospringrest.entity.Comment;
import com.example.demospringrest.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(Comment comment, Long id);

    List<CommentDto> getAllComments(Long postId);

    CommentDto getComment(Long postId, Long id);

    CommentDto updateCommnet(Long postId, Long id, Comment comment);

    void deleteComment(Long postId, Long id);

}
