package com.example.demospringrest.service;

import com.example.demospringrest.entity.Post;
import com.example.demospringrest.payload.PostRequest;
import com.example.demospringrest.payload.PostResponse;

public interface PostService {
    Post createPost(PostRequest request);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDirection);

    Post getPost(Long id);

    Post updatePost(Long id, Post post);

    void deletePost(Long id);
}
