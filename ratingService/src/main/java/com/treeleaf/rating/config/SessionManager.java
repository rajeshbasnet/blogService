package com.treeleaf.rating.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class SessionManager {
    public String getCurrentUserId() {
        log.info("Fetching current user from security context");
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
