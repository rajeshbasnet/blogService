package com.treeleaf.auth.services;

import com.treeleaf.auth.dto.AuthRequest;
import com.treeleaf.auth.dto.AuthResponse;
import com.treeleaf.auth.dto.User;

public interface AuthenticationService {
    void registerUser(User user);

    AuthResponse authenticate(AuthRequest authRequest);

    boolean isTokenValid(String token);
}
