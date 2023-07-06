package com.treeleaf.rating.filters;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Filtering request, {} with HttpMethod, {}", request.getRequestURI(), request.getMethod());

        Optional<String> id = Optional.ofNullable(request.getHeader("id"));
        Optional<String> role = Optional.ofNullable(request.getHeader("role"));

        if (id.isPresent() && role.isPresent()) {
            SecurityContextHolder.getContext().setAuthentication(createAuthentication(id.get(), role.get()));
            filterChain.doFilter(request, response);
        } else {
            log.warn("Incoming request with missing authorization header, Fiter action : false");
            filterChain.doFilter(request, response);
        }
    }

    private Authentication createAuthentication(String id, String role) {
        return new UsernamePasswordAuthenticationToken(id, null, getAuthorities(role));
    }

    private List<GrantedAuthority> getAuthorities(String role) {
        return Arrays.stream(role.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
