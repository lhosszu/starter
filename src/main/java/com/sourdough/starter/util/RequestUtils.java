package com.sourdough.starter.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUtils {

    public static String getAuthenticatedUserName() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                       .map(SecurityContext::getAuthentication)
                       .map(Authentication::getPrincipal)
                       .map(credential -> (org.springframework.security.core.userdetails.User) credential)
                       .map(org.springframework.security.core.userdetails.User::getUsername)
                       .orElse(null);
    }
}
