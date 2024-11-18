package com.sourdough.starter.user;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    default Optional<User> findEnabledByName(String name) {
        return findOne(Example.of(User.builder().name(name).enabled(Boolean.TRUE).build()));
    }

    default boolean userExists(String name) {
        return findOne(Example.of(User.builder().name(name).build())).isPresent();
    }
}
