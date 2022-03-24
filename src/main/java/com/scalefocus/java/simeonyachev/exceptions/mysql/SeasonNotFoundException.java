package com.scalefocus.java.simeonyachev.exceptions.mysql;

import com.scalefocus.java.simeonyachev.exceptions.ResourceNotFoundException;

public class SeasonNotFoundException extends ResourceNotFoundException {

    public SeasonNotFoundException(Integer id) {
        super("Cannot find season with id " + id);
    }
}
