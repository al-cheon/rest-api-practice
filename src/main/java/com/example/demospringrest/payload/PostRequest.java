package com.example.demospringrest.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostRequest {

    @NotEmpty(message = "title is empty")
    private String title;

    @NotEmpty(message = "content is empty")
    @Size(min = 3, max = 100, message = "size should have at least 3 characters")
    private String content;

    private Set<CommentDto> comments;

}
