package com.example.demospringrest.payload;

import lombok.Data;

@Data
public class SignUpDto {
    private String username;
    private String password;
    private String email;
}
