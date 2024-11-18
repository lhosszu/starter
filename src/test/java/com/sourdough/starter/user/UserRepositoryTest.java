package com.sourdough.starter.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Sql("classpath:fixtures/user-repository-test.sql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void canFindEnabledUserByName() {
        var user = userRepository.findEnabledByName("foo");

        assertThat(user).map(User::getName).get().isEqualTo("foo");
    }

    @Test
    void canDetermineUserExists() {
        var exists = userRepository.userExists("foo");

        assertThat(exists).isTrue();
    }

    @Test
    void canDisableUser() {
        var user = userRepository.disable("foo");

        assertThat(user).get()
                        .extracting(User::getName, User::getEnabled, User::getVersion)
                        .containsExactly("foo", false, 2L);
    }
}
