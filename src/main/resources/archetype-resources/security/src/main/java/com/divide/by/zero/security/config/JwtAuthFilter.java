package com.divide.by.zero.security.config;

import com.divide.by.zero.security.usersecurity.dao.JpaUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JpaUserDetailsService jpaUserDetailsService;
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String userEmail;
        String jwtToken;

        final Optional<String> token = getTokenFromCookieOrHeader(authHeader, request.getCookies());

        if(token.isPresent()) {
            jwtToken = token.get();
            userEmail = jwtUtils.extractUsername(jwtToken);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(userEmail);
                if (jwtUtils.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        }  else {
            filterChain.doFilter(request, response);
        }

    }

    private Optional<String> getTokenFromCookieOrHeader(String authHeader, Cookie[] cookies ) {
        final Optional<String> headerToken = getTokenFromHeader(authHeader);
        return headerToken.isPresent() ? headerToken : getTokenFromCookie(cookies);
    }

    private Optional<String> getTokenFromCookie(Cookie[] cookies) {

        if (cookies == null) {
            return  Optional.empty();
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("jwt")) {
                return Optional.of(cookie.getValue());
            }
        }
        return Optional.empty();
    }

    private Optional<String> getTokenFromHeader(String authHeader) {
        if (authHeader != null) {
            int space = authHeader.indexOf(' ');
            if (space > 0) {
                final String method = authHeader.substring(0, space);
                if ("Bearer".equalsIgnoreCase(method)) {
                    final String rawToken = authHeader.substring(space + 1);
                    return Optional.of(rawToken);
                }
            }
        }

        return Optional.empty();
    }
}
