package com.scalefocus.java.simeonyachev.exceptions.mysql;

import com.scalefocus.java.simeonyachev.exceptions.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(Long id) {
        super("Cannot find user with id " + id);
    }
}
