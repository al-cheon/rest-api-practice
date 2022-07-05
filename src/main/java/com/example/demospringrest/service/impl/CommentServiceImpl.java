package com.example.demospringrest.service.impl;

import com.example.demospringrest.entity.Comment;
import com.example.demospringrest.entity.Post;
import com.example.demospringrest.exception.ApiException;
import com.example.demospringrest.exception.ResourceNotFoundException;
import com.example.demospringrest.payload.CommentDto;
import com.example.demospringrest.repository.CommentRepository;
import com.example.demospringrest.repository.PostRepository;
import com.example.demospringrest.service.CommentService;
import com.example.demospringrest.utils.CommonConsts;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;

    private CommentRepository commentRepository;

    private ModelMapper mapper;

    @Autowired
    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(Comment comment, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, CommonConsts.ERROR_MESSASE_001));
        comment.setPost(post);
        Comment saved = commentRepository.save(comment);
        return mapper.map(saved, CommentDto.class);
    }

    @Override
    public List<CommentDto> getAllComments(Long postId) {
        List<Comment> commentList = commentRepository.findByPostId(postId);
        return commentList.stream().map(comment -> mapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getComment(Long postId, Long id) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", id));
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", id));
        if (!post.getId().equals(comment.getPost().getId())) {
            throw new RuntimeException();
        }
        return mapper.map(comment, CommentDto.class);
    }

    @Override
    public CommentDto updateCommnet(Long postId, Long id, Comment comment) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, CommonConsts.ERROR_MESSASE_001));
        Comment target = commentRepository.findById(id).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, CommonConsts.ERROR_MESSASE_001));
        if (!target.getPost().getId().equals(post.getId())) {
            throw new RuntimeException();
        }
        target.setReply(comment.getReply());
        Comment saved = commentRepository.save(target);
        return mapper.map(saved, CommentDto.class);
    }

    @Override
    public void deleteComment(Long postId, Long id) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, CommonConsts.ERROR_MESSASE_001));
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, CommonConsts.ERROR_MESSASE_001));

        if (!post.getId().equals(comment.getPost().getId())) {
            throw new RuntimeException();
        }
        commentRepository.delete(comment);
    }
}
