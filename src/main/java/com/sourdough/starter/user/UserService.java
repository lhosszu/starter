package com.sourdough.starter.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class contains (mostly) placeholder methods used to implement basic API functionalities.
 */
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByName(String name) {
        return Optional.ofNullable(userRepository.findEnabledByName(name))
                       .orElseThrow(() -> new UserException("User %s missing or disabled".formatted(name)));

    }
}
