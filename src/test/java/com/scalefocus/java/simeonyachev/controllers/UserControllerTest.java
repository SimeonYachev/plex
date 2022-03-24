package com.scalefocus.java.simeonyachev.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.java.simeonyachev.domain.mysql.User;
import com.scalefocus.java.simeonyachev.exceptions.mysql.UserNotFoundException;
import com.scalefocus.java.simeonyachev.security.jwt.JwtUtil;
import com.scalefocus.java.simeonyachev.security.requests.AuthenticationRequest;
import com.scalefocus.java.simeonyachev.services.mysql.UserService;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final String USERS_ENDPOINT = "/api/users";
    private static final String USER_ID_ENDPOINT = "/api/users/14";
    private static AuthenticationRequest REQUEST;
    private static String REQUEST_AS_JSON;
    private static User EXPECTED_USER;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userServiceMock;

    @MockBean
    private UserDetailsService userDetailsServiceMock;

    @MockBean
    private JwtUtil jwtUtilMock;

    @BeforeAll
    static void setExpectedInputAndOutput() throws JsonProcessingException {
        REQUEST = new AuthenticationRequest("username", "password");
        REQUEST_AS_JSON = new ObjectMapper().writeValueAsString(REQUEST);
        EXPECTED_USER = new User(REQUEST.getUsername(), REQUEST.getPassword());
    }

    @Test
    @WithMockUser(username = "username")
    void getUsersSuccessfully() throws Exception {
        Collection<User> expectedUsers = Lists.list(EXPECTED_USER);
        when(userServiceMock.getUsers()).thenReturn(expectedUsers);

        mockMvc.perform(get(USERS_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(1)))
                .andExpect(jsonPath("$[0].username").value("username"));

        verify(userServiceMock).getUsers();
    }

    @Test
    @WithMockUser(username = "username")
    void getUserByIdSuccessfully() throws Exception {
        when(userServiceMock.getById(anyLong())).thenReturn(EXPECTED_USER);

        mockMvc.perform(get(USER_ID_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$.username").value("username"));

        verify(userServiceMock).getById(anyLong());
    }

    @Test
    @WithMockUser(username = "username")
    void getUserByIdNotFound() throws Exception {
        when(userServiceMock.getById(anyLong())).thenThrow(new UserNotFoundException(1L));

        mockMvc.perform(get(USER_ID_ENDPOINT))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cannot find user with id " + 1L));

        verify(userServiceMock).getById(anyLong());
    }

    @Test
    void registerUserSuccessfully() throws Exception {
        when(userServiceMock.register(any(User.class))).thenReturn(EXPECTED_USER);

        mockMvc.perform(MockMvcRequestBuilders
                    .post(USERS_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(REQUEST_AS_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$.username").value("username"));

        verify(userServiceMock).register(any(User.class));
    }

    @Test
    void registerUserAlreadyRegistered() throws Exception {
        when(userServiceMock.register(any())).thenThrow(SQLIntegrityConstraintViolationException.class);

        mockMvc.perform(post(USERS_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(REQUEST_AS_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().string("User " + REQUEST.getUsername() + " already registered"));

        verify(userServiceMock).register(any());
    }

    @Test
    @WithMockUser(username = "username")
    void deleteUserSuccessfully() throws Exception {
        String expectedResponse = "User with id " + 1L + " deleted successfully";
        when(userServiceMock.delete(anyLong())).thenReturn(expectedResponse);

        mockMvc.perform(delete(USER_ID_ENDPOINT))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));

        verify(userServiceMock).delete(anyLong());
    }

    @Test
    @WithMockUser(username = "username")
    void deleteUserNotFound() throws Exception {
        when(userServiceMock.delete(anyLong())).thenThrow(new UserNotFoundException(1L));

        mockMvc.perform(delete(USER_ID_ENDPOINT))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cannot find user with id " + 1L));

        verify(userServiceMock).delete(anyLong());
    }
}