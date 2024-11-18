package com.sourdough.starter.user.dto;

import com.sourdough.starter.user.User;

public record UserResponse(String name) {

    public static UserResponse fromModel(User user) {
        return new UserResponse(user.getName());
    }
}
