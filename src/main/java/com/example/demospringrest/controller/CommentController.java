package com.example.demospringrest.controller;

import com.example.demospringrest.entity.Comment;
import com.example.demospringrest.payload.CommentDto;
import com.example.demospringrest.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody Comment comment, @PathVariable(name = "postId") Long postId) {
        CommentDto saved = commentService.createComment(comment, postId);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllComments(@PathVariable(name = "postId") Long postId) {
        List<CommentDto> comments = commentService.getAllComments(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable(name = "postId") Long postId, @PathVariable(name = "id") Long id) {
        CommentDto comment = commentService.getComment(postId, id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("postId") Long postId, @PathVariable(name = "id") Long id , @RequestBody Comment comment) {
        CommentDto updated = commentService.updateCommnet(postId, id, comment);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("postId") Long postId, @PathVariable(name = "id") Long id) {
        commentService.deleteComment(postId, id);
        return ResponseEntity.ok("Delete Success!!");
    }


}
