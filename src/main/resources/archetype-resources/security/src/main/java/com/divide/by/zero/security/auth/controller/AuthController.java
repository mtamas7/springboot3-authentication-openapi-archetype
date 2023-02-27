package com.divide.by.zero.security.auth.controller;

import com.divide.by.zero.security.auth.service.AuthService;
import com.divide.by.zero.security.exception.AuthenticationException;
import ${groupId}.openapi.api.AuthenticationApi;
import ${groupId}.openapi.model.AuthenticationRequest;
import ${groupId}.openapi.model.AuthenticationResponse;
import ${groupId}.openapi.model.BaseResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthenticationApi {

    private final HttpServletResponse response;

    private final AuthService authService;

    @Override
    public ResponseEntity<BaseResponse> authenticateForCookie(final AuthenticationRequest authenticationRequest) {
        try {
            Cookie cookie = authService.authenticateForCookie(authenticationRequest);
            response.addCookie(cookie);
            return ResponseEntity.ok(new BaseResponse());
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<AuthenticationResponse> authenticateForToken(final AuthenticationRequest authenticationRequest) {
        try {
            return ResponseEntity.ok(authService.authenticateForToken(authenticationRequest));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
