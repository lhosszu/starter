package com.sourdough.starter.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @GetMapping("{name}")
    @Operation(summary = "Fetch user by name", tags = {"users"})
    public ResponseEntity<UserResponse> getUser(@Schema(example = "admin") @PathVariable String name) {
        User user = userService.findByName(name);
        return ResponseEntity.ok(UserResponse.fromModel(user));
    }
}
