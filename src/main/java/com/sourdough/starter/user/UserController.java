package com.sourdough.starter.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.sourdough.starter.user.dto.CreateUserRequest;
import com.sourdough.starter.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tags(@Tag(name = "Users"))
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @GetMapping("{name}")
    @Operation(summary = "Fetch user by name")
    public ResponseEntity<UserResponse> getUser(@Schema(example = "admin") @PathVariable String name) {
        User user = userService.find(name);
        return ResponseEntity.ok(UserResponse.fromModel(user));
    }

    @PostMapping
    @Operation(summary = "Create new user")
    public ResponseEntity<UserResponse> postUser(@RequestBody CreateUserRequest userRequest) {
        User user = userService.create(userRequest.name(), userRequest.rawPassword());
        return ResponseEntity.ok(UserResponse.fromModel(user));
    }

    @PatchMapping(value = "{name}", consumes = "application/merge-patch+json")
    @Operation(summary = "Patch user")
    public ResponseEntity<UserResponse> patchUser(@PathVariable String name, @RequestBody JsonNode patch) {
        User user = userService.patch(name, patch);
        return ResponseEntity.ok(UserResponse.fromModel(user));
    }
}
