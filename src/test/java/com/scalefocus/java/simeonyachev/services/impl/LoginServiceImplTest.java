package com.scalefocus.java.simeonyachev.services.impl;

import com.scalefocus.java.simeonyachev.security.jwt.JwtUtil;
import com.scalefocus.java.simeonyachev.security.requests.AuthenticationRequest;
import com.scalefocus.java.simeonyachev.security.responses.AuthenticationResponse;
import com.scalefocus.java.simeonyachev.services.LoginService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoginServiceImplTest {

    private static AuthenticationRequest REQUEST;

    private AuthenticationManager authenticationManagerMock;
    private UserDetailsService userDetailsServiceMock;
    private JwtUtil jwtUtilMock;
    private LoginService loginService;

    @BeforeAll
    static void setRequest() {
        REQUEST = new AuthenticationRequest("username", "password");
    }

    @BeforeEach
    void setUp() {
        authenticationManagerMock = mock(AuthenticationManager.class);
        userDetailsServiceMock = mock(UserDetailsService.class);
        jwtUtilMock = mock(JwtUtil.class);
        loginService = new LoginServiceImpl(authenticationManagerMock, userDetailsServiceMock, jwtUtilMock);
    }

    @Test
    void loginSuccessfully() {
        when(jwtUtilMock.generateToken(any())).thenReturn("expectedJwt");

        AuthenticationResponse expected = new AuthenticationResponse("expectedJwt");
        AuthenticationResponse actual = loginService.login(REQUEST);

        verify(jwtUtilMock).generateToken(any());
        assertEquals(expected, actual, "The authentication response doesn't match the one expected.");
    }

    @Test
    void loginBadCredentials() {
        when(authenticationManagerMock.authenticate(any())).thenThrow(BadCredentialsException.class);

        assertThrows(BadCredentialsException.class, () -> loginService.login(REQUEST));
        verify(authenticationManagerMock).authenticate(any());
    }

    @Test
    void loginUsernameNotFound() {
        when(userDetailsServiceMock.loadUserByUsername(anyString())).thenThrow(UsernameNotFoundException.class);

        assertThrows(UsernameNotFoundException.class, () -> loginService.login(REQUEST));
        verify(userDetailsServiceMock).loadUserByUsername(anyString());
    }
}