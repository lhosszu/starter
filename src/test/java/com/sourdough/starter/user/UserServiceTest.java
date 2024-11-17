package com.sourdough.starter.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void whenFindByName_thenReturnUser() {
        Mockito.when(userRepository.findEnabledByName("foo")).thenReturn(Optional.of(new User(null, "foo", "bar", true)));

        User actualUser = new UserService(userRepository).findByName("foo");

        assertThat(actualUser).isEqualTo(new User(null, "foo", "bar", true));
    }

    @Test
    void whenFindMissingUserByName_thenThrowException() {
        UserService userService = new UserService(userRepository);

        assertThatThrownBy(() -> userService.findByName("lipsum")).isInstanceOf(UserException.class);
    }

    @Test
    void whenFindDisabledUserByName_thenThrowException() {
        Mockito.when(userRepository.findEnabledByName("foo")).thenReturn(Optional.empty());

        UserService userService = new UserService(userRepository);

        assertThatThrownBy(() -> userService.findByName("foo")).isInstanceOf(UserException.class);
    }
}
