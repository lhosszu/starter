package com.sourdough.starter.user;

public record UserResponse(String name) {

    static UserResponse fromModel(User user) {
        return new UserResponse(user.getName());
    }
}
