package com.treeleaf.auth.controllers;


import com.treeleaf.auth.Util.Util;
import com.treeleaf.auth.dto.AuthRequest;
import com.treeleaf.auth.dto.AuthResponse;
import com.treeleaf.auth.dto.User;
import com.treeleaf.auth.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Log4j2
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        AuthResponse authResponse = authenticationService.authenticate(request);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        authenticationService.registerUser(user);
        return ResponseEntity.ok(Map.of("message", Util.USER_REGISTERED));
    }

}
