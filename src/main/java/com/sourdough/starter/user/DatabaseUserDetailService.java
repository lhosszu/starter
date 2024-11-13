package com.sourdough.starter.user;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Loading details of enabled users based on username in the Basic Authorization header.
 */
@Service
@AllArgsConstructor
public class DatabaseUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findEnabledByName(username))
                       .map(user -> new User(user.getName(), "{bcrypt}%s".formatted(user.getPassword()), List.of(new SimpleGrantedAuthority("USER"))))
                       .orElseThrow(() -> new InsufficientAuthenticationException("Cannot find enabled user: " + username));
    }
}
