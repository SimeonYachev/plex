package com.scalefocus.java.simeonyachev.services.mysql.impl;

import com.scalefocus.java.simeonyachev.domain.mysql.User;
import com.scalefocus.java.simeonyachev.exceptions.mysql.UserNotFoundException;
import com.scalefocus.java.simeonyachev.repositories.mysql.UserRepository;
import com.scalefocus.java.simeonyachev.services.mysql.UserService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private static User EXPECTED_USER;

    UserRepository userRepositoryMock;
    UserService userService;

    @BeforeAll
    static void setExpectedUser() {
        EXPECTED_USER = new User("username", "password");
        EXPECTED_USER.setId(1L);
    }

    @BeforeEach
    void setUp() {
        userRepositoryMock = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepositoryMock);
    }

    @Test
    void getUsersSuccessfully() {
        List<User> expectedUsers = Lists.list(EXPECTED_USER);

        when(userRepositoryMock.findAll()).thenReturn(expectedUsers);

        Collection<User> actualUsers = userService.getUsers();

        verify(userRepositoryMock).findAll();

        assertEquals(expectedUsers, actualUsers, "Users found don't match the ones expected.");
    }

    @Test
    void registerUserSuccessfully() throws SQLIntegrityConstraintViolationException {
        when(userRepositoryMock.save(any(User.class))).thenReturn(EXPECTED_USER);

        User actualUser = userService.register(EXPECTED_USER);

        verify(userRepositoryMock).save(EXPECTED_USER);

        assertEquals(EXPECTED_USER, actualUser, "Registered user doesn't match the one expected.");
    }

    @Test
    void getUserByIdSuccessfully() {
        long anyLong = anyLong();
        when(userRepositoryMock.findById(anyLong)).thenReturn(Optional.of(EXPECTED_USER));

        User actualUser = userService.getById(anyLong);

        verify(userRepositoryMock).findById(anyLong);

        assertEquals(EXPECTED_USER, actualUser, "Wrong user found by id.");
    }

    @Test
    void getUserByIdNotFound() {
        long anyLong = anyLong();
        when(userRepositoryMock.findById(anyLong)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userService.getById(anyLong));
        verify(userRepositoryMock).findById(anyLong);
    }

    @Test
    void getUserByUsernameSuccessfully() {
        String anyString = anyString();
        when(userRepositoryMock.findByUsername(anyString)).thenReturn(Optional.of(EXPECTED_USER));

        User actualUser = userService.getByUsername(anyString);

        verify(userRepositoryMock).findByUsername(anyString);

        assertEquals(EXPECTED_USER, actualUser, "Wrong user found by username.");
    }

    @Test
    void getUserByUsernameNotFound() {
        String anyString = anyString();
        when(userRepositoryMock.findByUsername(anyString)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userService.getByUsername(anyString));
        verify(userRepositoryMock).findByUsername(anyString);
    }

    @Test
    void deleteUserSuccessfully() {
        String expectedMessage = "User with id " + EXPECTED_USER.getId() + " deleted successfully";

        String actualMessage = userService.delete(1L);

        assertEquals(expectedMessage, actualMessage, "Wrong message after user deletion.");
    }

    @Test
    void deleteUserDoesNotExist() {
        doThrow(EmptyResultDataAccessException.class).when(userRepositoryMock).deleteById(anyLong());

        assertThrows(UserNotFoundException.class, () -> userService.delete(anyLong()));
        verify(userRepositoryMock).deleteById(anyLong());
    }
}