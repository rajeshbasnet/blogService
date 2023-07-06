package com.treeleaf.auth.dao;


import com.treeleaf.auth.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationDaoImpl implements AuthenticationDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void registerUser(User user) {
        jdbcTemplate.update("""
                INSERT INTO USER(id, username, email, password, role) VALUES (?, ?, ?, ?, ?)
                """, user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getRole());
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM user WHERE email = ?",
                    new Object[]{email}, new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException exception) {
            log.error("Cannot find user by email: {}", email);
            return null;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM user WHERE username = ?",
                    new Object[]{username}, new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException exception) {
            log.error("Cannot find user by username: {}", username);
            return null;
        }
    }

}

