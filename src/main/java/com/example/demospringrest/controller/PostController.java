package com.example.demospringrest.controller;


import com.example.demospringrest.entity.Post;
import com.example.demospringrest.payload.PostResponse;
import com.example.demospringrest.service.PostService;
import com.example.demospringrest.utils.CommonConsts;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        Post saved = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts(
            @RequestParam(name = "pageNo", required = false, defaultValue = CommonConsts.PAGE_NO) int pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = CommonConsts.PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy" ,required = false, defaultValue = CommonConsts.SORT_BY) String sortBy,
            @RequestParam(value = "sortDirection", required = false, defaultValue = CommonConsts.SORT_DIRECTION) String sortDirection
    ) {
        PostResponse response = postService.getAllPosts(pageNo, pageSize, sortBy, sortDirection);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable(name = "id") Long id) {
        Post post = postService.getPost(id);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@RequestBody Post post, @PathVariable(name = "id") Long id) {
        Post updated = postService.updatePost(id, post);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable(name = "id") Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Delete Success!");
    }


}
