package com.treeleaf.blog.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Log4j2
public class SessionManager {
    public String getCurrentUserId() {
        log.info("Fetching current user from security context");
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String getUserRole() {
        log.info("Fetching current user from security context");
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());
    }
}
