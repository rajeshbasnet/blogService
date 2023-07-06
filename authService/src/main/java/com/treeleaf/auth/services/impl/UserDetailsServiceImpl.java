package com.treeleaf.auth.services.impl;

import com.treeleaf.auth.Util.Util;
import com.treeleaf.auth.dao.AuthenticationDao;
import com.treeleaf.auth.dto.CustomUserDetails;
import com.treeleaf.auth.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthenticationDao authenticationDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(authenticationDao.getUserByUsername(username));

        if(user.isPresent()) {
            return new CustomUserDetails(user.get());
        }else {
            throw new BadCredentialsException(Util.CANNOT_FIND_USER_BY_USERNAME);
        }
    }
}
