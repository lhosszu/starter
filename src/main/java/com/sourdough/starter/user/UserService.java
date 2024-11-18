package com.sourdough.starter.user;

import com.sourdough.starter.util.RequestUtils;
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

        return userRepository.create(name, encodePassword(rawPassword));
    }

    public User disable(String name) {
        if (name.equalsIgnoreCase(RequestUtils.getAuthenticatedUserName())) {
            throw new UserException("Cannot update authenticated user");
        }

        return userRepository.disable(name)
                             .orElseThrow(() -> new UserException("User %s not found".formatted(name)));
    }

    private String encodePassword(String rawPassword) {
        return new BCryptPasswordEncoder().encode(rawPassword);
    }
}
