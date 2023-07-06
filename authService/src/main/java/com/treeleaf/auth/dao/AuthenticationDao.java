package com.treeleaf.auth.dao;

import com.treeleaf.auth.dto.User;

public interface AuthenticationDao {
    void registerUser(User user);

    User getUserByEmail(String email);

    User getUserByUsername(String username);
}
