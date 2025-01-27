package com.sourdough.starter.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.sourdough.starter.util.RequestUtils.getAuthenticatedUserName;
import static com.sourdough.starter.util.RequestUtils.getObjectMapper;

@Service
@AllArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public User findEnabled(String name) {
        logger.debug("Finding user {}", name);
        return userRepository.findEnabledByName(name)
                             .orElseThrow(() -> new UserException("User %s missing or disabled".formatted(name)));
    }

    public User create(String name, String rawPassword) {
        if (userRepository.userExists(name)) {
            throw new UserException("User %s already exists".formatted(name));
        }

        logger.info("Creating user {}", name);
        return userRepository.create(name, new BCryptPasswordEncoder().encode(rawPassword));
    }

    /**
     * <a href="https://datatracker.ietf.org/doc/html/rfc7386">Perform JSON Merge Patch</a>
     */
    public User patch(String name, JsonNode patch) {
        if (name.equalsIgnoreCase(getAuthenticatedUserName())) {
            throw new UserException("Cannot patch authenticated user");
        }

        logger.info("Updating user {}", name);
        return userRepository.findByName(name)
                             .map(target -> applyPatch(target, patch))
                             .map(userRepository::saveAndFlush)
                             .orElseThrow(() -> new UserException("User %s not found".formatted(name)));
    }

    private User applyPatch(User target, JsonNode patch) {
        try {
            JsonNode patched = JsonPatch.fromJson(patch).apply(getObjectMapper().convertValue(target, JsonNode.class));
            return getObjectMapper().treeToValue(patched, User.class);
        } catch (Exception e) {
            logger.error("Cannot patch user {}", target.getName(), e);
            throw new UserException("Cannot patch user %s".formatted(target.getName()));
        }
    }
}
