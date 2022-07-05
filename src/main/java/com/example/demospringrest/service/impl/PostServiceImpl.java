package com.example.demospringrest.service.impl;

import com.example.demospringrest.entity.Post;
import com.example.demospringrest.exception.ApiException;
import com.example.demospringrest.payload.PostRequest;
import com.example.demospringrest.payload.PostResponse;
import com.example.demospringrest.repository.PostRepository;
import com.example.demospringrest.service.PostService;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public Post createPost(PostRequest request) {
        Post post = mapper.map(request, Post.class);
        return postRepository.save(post);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.Direction.ASC.name().equalsIgnoreCase(sortDirection) ?  Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> page = postRepository.findAll(pageable);

        PostResponse response = new PostResponse();
        List<Post> content = page.getContent();
        long totalElements = page.getTotalElements();
        boolean last = page.isLast();
        int totalPages = page.getTotalPages();


        response.setPostList(content);
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setTotalElements(totalElements);
        response.setTotalPages(totalPages);
        response.setLast(last);

        return response;
    }

    @Override
    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Api Exception"));
    }

    @Override
    public Post updatePost(Long id, Post post) {
        Post existed = postRepository.findById(id).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Api Exception"));
        existed.setTitle(post.getTitle());
        existed.setComments(post.getComments());
        return postRepository.save(existed);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
