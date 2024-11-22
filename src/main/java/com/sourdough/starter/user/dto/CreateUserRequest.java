package com.sourdough.starter.user.dto;

public record CreateUserRequest(String name, String rawPassword) {
}
