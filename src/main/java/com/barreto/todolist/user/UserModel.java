package com.barreto.todolist.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserModel {
    private String name;
    private String username;
    private String password;
    private LocalDateTime createdAt;
}
