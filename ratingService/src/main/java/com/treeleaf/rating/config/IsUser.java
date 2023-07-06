package com.treeleaf.rating.config;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@PreAuthorize("hasAuthority('USER')")
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IsUser {
}
