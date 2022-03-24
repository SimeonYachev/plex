package com.scalefocus.java.simeonyachev.exceptions.mysql;

import com.scalefocus.java.simeonyachev.exceptions.ResourceNotFoundException;

public class MovieNotFoundException extends ResourceNotFoundException {

    public MovieNotFoundException(Integer id) {
        super("Cannot find movie with id " + id);
    }
}
