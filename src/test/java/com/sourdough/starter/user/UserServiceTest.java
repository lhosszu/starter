package com.sourdough.starter.user;

import org.junit.jupiter.api.BeforeEach;
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
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void whenFind_thenReturnUser() {
        Mockito.when(userRepository.findEnabledByName("foo")).thenReturn(Optional.of(User.enabled().name("foo").password("bar").build()));

        User actualUser = userService.find("foo");

        assertThat(actualUser).isEqualTo(User.enabled().name("foo").password("bar").build());
    }

    @Test
    void whenFindMissingUserByName_thenThrowException() {
        assertThatThrownBy(() -> userService.find("lipsum")).isInstanceOf(UserException.class);
    }

    @Test
    void whenFindDisabledUserByName_thenThrowException() {
        Mockito.when(userRepository.findEnabledByName("foo")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.find("foo")).isInstanceOf(UserException.class);
    }

    @Test
    void givenUserAlreadyExists_whenCreateNewUser_thenThrowException() {
        Mockito.when(userRepository.userExists("foo")).thenReturn(true);

        assertThatThrownBy(() -> userService.create("foo", "bar")).isInstanceOf(UserException.class);
    }
}
