package com.scalefocus.java.simeonyachev.controllers;

import com.scalefocus.java.simeonyachev.domain.mysql.User;
import com.scalefocus.java.simeonyachev.exceptions.UserAlreadyRegisteredException;
import com.scalefocus.java.simeonyachev.security.requests.AuthenticationRequest;
import com.scalefocus.java.simeonyachev.services.mysql.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @ApiOperation(value = "Returns all registered users")
    public ResponseEntity<Collection<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns user information by given id")
    public ResponseEntity<User> getUserById(@ApiParam(value = "id of the user whose info you need to retrieve", required = true)
                                                @PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping
    @ApiOperation(value = "Registers a user",
            notes = "Provide username and password")
    public ResponseEntity<User> register(@ApiParam("username and password in the form of JSON object")
                                             @RequestBody AuthenticationRequest request) {
        User user = new User(request.getUsername(), request.getPassword());
        try {
            return ResponseEntity.ok(userService.register(user));
        } catch (SQLIntegrityConstraintViolationException exception) {
            throw new UserAlreadyRegisteredException(user.getUsername());
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes an user by given id")
    public ResponseEntity<String> deleteUser(@ApiParam(value = "id of the user that you want to delete", required = true)
                                                 @PathVariable Long id) {
        return ResponseEntity.ok(userService.delete(id));
    }
}
