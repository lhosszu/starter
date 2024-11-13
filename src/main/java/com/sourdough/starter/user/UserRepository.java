package com.sourdough.starter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("FROM users WHERE name = ?1 AND enabled = TRUE")
    User findEnabledByName(String name);
}
