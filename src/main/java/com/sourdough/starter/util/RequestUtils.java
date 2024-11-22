package com.sourdough.starter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUtils {

    private static final ThreadLocal<ObjectMapper> OBJECT_MAPPER = ThreadLocal.withInitial(ObjectMapper::new);

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER.get();
    }

    public static String getAuthenticatedUserName() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                       .map(SecurityContext::getAuthentication)
                       .map(Authentication::getPrincipal)
                       .map(credential -> (org.springframework.security.core.userdetails.User) credential)
                       .map(org.springframework.security.core.userdetails.User::getUsername)
                       .orElse(null);
    }
}
