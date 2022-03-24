package com.scalefocus.java.simeonyachev.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException {

    public UserAlreadyRegisteredException(String username) {
        super("User " + username + " already registered");
    }
}
