package com.sourdough.starter.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This class contains (mostly) placeholder methods used to implement basic API functionalities.
 */
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User find(String name) {
        return userRepository.findEnabledByName(name)
                             .orElseThrow(() -> new UserException("User %s missing or disabled".formatted(name)));

    }

    public User create(String name, String rawPassword) {
        if (userRepository.userExists(name)) {
            throw new UserException("User %s already exists".formatted(name));
        }

        return userRepository.save(User.builder()
                                       .name(name)
                                       .password(encodePassword(rawPassword))
                                       .build());
    }

    private String encodePassword(String rawPassword) {
        return new BCryptPasswordEncoder().encode(rawPassword);
    }
}
