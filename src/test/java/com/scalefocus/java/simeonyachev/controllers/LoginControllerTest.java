package com.scalefocus.java.simeonyachev.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.java.simeonyachev.security.jwt.JwtUtil;
import com.scalefocus.java.simeonyachev.security.requests.AuthenticationRequest;
import com.scalefocus.java.simeonyachev.security.responses.AuthenticationResponse;
import com.scalefocus.java.simeonyachev.services.LoginService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    private static final String LOGIN_ENDPOINT = "/api/login";
    private static AuthenticationRequest REQUEST;
    private static String REQUEST_AS_JSON;

    @MockBean
    private LoginService loginService;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void setRequest() throws JsonProcessingException {
        REQUEST = new AuthenticationRequest("username", "password");
        REQUEST_AS_JSON = new ObjectMapper().writeValueAsString(REQUEST);
    }

    @Test
    void loginSuccessfully() throws Exception {
        when(loginService.login(REQUEST)).thenReturn(new AuthenticationResponse("jwt"));


        mockMvc.perform(post(LOGIN_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(REQUEST_AS_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void loginBadCredentials() throws Exception {
        when(loginService.login(REQUEST)).thenThrow(BadCredentialsException.class);

        mockMvc.perform(post(LOGIN_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(REQUEST_AS_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid username or password."));
    }
}