package com.divide.by.zero.security.auth.service;

import ${groupId}.openapi.model.AuthenticationRequest;
import ${groupId}.openapi.model.AuthenticationResponse;
import jakarta.servlet.http.Cookie;

public interface AuthService {
    AuthenticationResponse authenticateForToken(AuthenticationRequest authenticationRequest);

    Cookie authenticateForCookie(AuthenticationRequest authenticationRequest);
}
