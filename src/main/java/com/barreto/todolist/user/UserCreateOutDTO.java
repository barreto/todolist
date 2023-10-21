package com.barreto.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserCreateOutDTO(
        UUID id,
        String name,
        String username,
        LocalDateTime createdAt
) {
    public UserCreateOutDTO(UserModel userModel) {
        this(userModel.getId(), userModel.getName(), userModel.getUsername(), userModel.getCreatedAt());
    }
}
