package com.divide.by.zero.security.auth.service;

import com.divide.by.zero.security.config.JwtUtils;
import com.divide.by.zero.security.exception.AuthenticationException;
import com.divide.by.zero.security.usersecurity.dao.JpaUserDetailsService;
import ${groupId}.openapi.model.AuthenticationRequest;
import ${groupId}.openapi.model.AuthenticationResponse;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private static final String JWT_COOKIE_NAME = "jwt";

    private final AuthenticationManager authenticationManager;

    private final JpaUserDetailsService jpaUserDetailsService;

    private final JwtUtils jwtUtils;

    @Override
    public AuthenticationResponse authenticateForToken(final AuthenticationRequest authenticationRequest) {

        final UserDetails userDetails = authenticateAndGetUserDetails(authenticationRequest);
        final String jwt = jwtUtils.generateToken(userDetails);

        final AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwt);

        return authenticationResponse;
    }


    @Override
    public Cookie authenticateForCookie(final AuthenticationRequest authenticationRequest) {
        final UserDetails userDetails = authenticateAndGetUserDetails(authenticationRequest);
        final String jwt = jwtUtils.generateToken(userDetails);

        final Cookie cookie = new Cookie(JWT_COOKIE_NAME, jwt);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;

    }

    private UserDetails authenticateAndGetUserDetails(final AuthenticationRequest authenticationRequest) {
        authenticate(authenticationRequest);
        final UserDetails userDetails = getUserDetails(authenticationRequest);

        if (userDetails == null) {
            throw new AuthenticationException("Unknown user!");
        }

        return userDetails;
    }

    private UserDetails getUserDetails(final AuthenticationRequest authenticationRequest) {
        return jpaUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());
    }

    private void authenticate(final AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                authenticationRequest.getPassword(),
                new ArrayList<>()));
    }
}
