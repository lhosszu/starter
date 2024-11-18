package com.sourdough.starter.user;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    default Optional<User> findEnabledByName(String name) {
        return findOne(Example.of(User.enabled().name(name).build()));
    }

    default boolean userExists(String name) {
        return findOne(Example.of(User.builder().name(name).build())).isPresent();
    }

    default Optional<User> disable(String name) {
        return findEnabledByName(name)
                .map(user -> user.withEnabled(Boolean.FALSE))
                .map(this::saveAndFlush);
    }

    default User create(String name, String encodedPassword) {
        return save(User.builder()
                        .name(name)
                        .password(encodedPassword)
                        .enabled(Boolean.TRUE)
                        .build());
    }
}
