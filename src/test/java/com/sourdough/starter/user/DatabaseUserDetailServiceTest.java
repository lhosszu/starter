package com.sourdough.starter.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DatabaseUserDetailServiceTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void whenUserFound_thenLoadUser() {
        when(userRepository.findEnabledByName("foo")).thenReturn(Optional.of(User.enabled().name("foo").password("pw").build()));
        UserDetails userDetails = new DatabaseUserDetailService(userRepository).loadUserByUsername("foo");

        assertThat(userDetails).extracting("username", "password").containsExactly("foo", "{bcrypt}pw");
    }

    @Test
    void whenUserMissing_thenThrowException() {
        when(userRepository.findEnabledByName(any())).thenReturn(Optional.empty());
        DatabaseUserDetailService userDetailsService = new DatabaseUserDetailService(userRepository);

        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("foobar")).isInstanceOf(AuthenticationException.class);
    }
}
