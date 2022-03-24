package com.scalefocus.java.simeonyachev.exceptions.sqlite;

import com.scalefocus.java.simeonyachev.exceptions.ResourceNotFoundException;

public class StreamTypeNotFoundException extends ResourceNotFoundException {

    public StreamTypeNotFoundException(Integer id) {
        super("Cannot find stream type with id " + id);
    }

    public StreamTypeNotFoundException(String name) {
        super("Cannot find stream type with name " + name);
    }
}
