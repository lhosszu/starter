package com.sourdough.starter.user;

import com.sourdough.starter.user.dto.UserRequest;
import com.sourdough.starter.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @GetMapping("{name}")
    @Operation(summary = "Fetch user by name", tags = {"users"})
    public ResponseEntity<UserResponse> getUser(@Schema(example = "admin") @PathVariable String name) {
        User user = userService.find(name);
        return ResponseEntity.ok(UserResponse.fromModel(user));
    }

    @PostMapping
    @Operation(summary = "Create new user", tags = {"users"})
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        User user = userService.create(userRequest.name(), userRequest.rawPassword());
        return ResponseEntity.ok(UserResponse.fromModel(user));
    }

    @PutMapping("{name}/disable")
    @Operation(summary = "Disable user", tags = {"users"})
    public ResponseEntity<UserResponse> disableUser(@PathVariable String name) {
        User user = userService.disable(name);
        return ResponseEntity.ok(UserResponse.fromModel(user));
    }
}
