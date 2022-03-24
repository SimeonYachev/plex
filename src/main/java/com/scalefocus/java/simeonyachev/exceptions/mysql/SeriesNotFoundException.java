package com.scalefocus.java.simeonyachev.exceptions.mysql;

import com.scalefocus.java.simeonyachev.exceptions.ResourceNotFoundException;

public class SeriesNotFoundException extends ResourceNotFoundException {

    public SeriesNotFoundException(Integer id) {
        super("Cannot find series with id " + id);
    }
}
