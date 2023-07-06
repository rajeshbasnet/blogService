package com.treeleaf.auth.services.impl;

import com.treeleaf.auth.Util.JWTUtil;
import com.treeleaf.auth.Util.Util;
import com.treeleaf.auth.dao.AuthenticationDao;
import com.treeleaf.auth.dto.AuthRequest;
import com.treeleaf.auth.dto.AuthResponse;
import com.treeleaf.auth.dto.CustomUserDetails;
import com.treeleaf.auth.dto.User;
import com.treeleaf.auth.exceptions.UserAlreadyExistsException;
import com.treeleaf.auth.services.AuthenticationService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationDao authenticationDao;

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    @Override
    public void registerUser(User user) {
        if (checkIfUserExists(user.getEmail())) {
            throw new UserAlreadyExistsException(Util.USER_ALREADY_EXISTS);
        }

        String userId = UUID.randomUUID().toString();
        user.setId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        authenticationDao.registerUser(user);
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        System.out.println(authRequest.toString());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        String token = generateToken(authentication);
        return new AuthResponse(token);
    }

    private boolean checkIfUserExists(String email) {
        Optional<User> user = Optional.ofNullable(authenticationDao.getUserByEmail(email));
        return user.isPresent();
    }

    private String generateToken(Authentication authentication) {
        final CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", userDetails.getId());
        payload.put("role", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining()));

        //Sending jwt token as a proper response
        return jwtUtil.generateToken(userDetails.getUsername(), payload);
    }
}
