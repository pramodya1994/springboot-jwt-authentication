package com.auth0.samples.authapi.springbootauthupdated.security;

import com.auth0.samples.authapi.springbootauthupdated.user.ApplicationUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.userdetails.User;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.auth0.jwt.JWT;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.auth0.samples.authapi.springbootauthupdated.security.SecurityConstants.EXPIRATION_TIME;
import static com.auth0.samples.authapi.springbootauthupdated.security.SecurityConstants.HEADER_STRING;
import static com.auth0.samples.authapi.springbootauthupdated.security.SecurityConstants.SECRET;
import static com.auth0.samples.authapi.springbootauthupdated.security.SecurityConstants.TOKEN_PREFIX;

/**
 * Filter responsible for authenticating users.
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Parse the user's credentials and issue them to the AuthenticationManager.
     */
    @Override public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        try {
            ApplicationUser creds = new ObjectMapper().readValue(req.getInputStream(), ApplicationUser.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Called when a user successfully logs in. We use this method to generate a JWT for this user.
     */
    @Override protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
            Authentication auth) {
        String token = JWT.create().withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
