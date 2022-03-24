package com.scalefocus.java.simeonyachev.exceptions.sqlite;

import com.scalefocus.java.simeonyachev.exceptions.ResourceNotFoundException;

public class MetadataItemNotFoundException extends ResourceNotFoundException {

    public MetadataItemNotFoundException(String metadataType, String parentType, Integer id) {
        super(String.format("Cannot find %s of %s with id %d", metadataType, parentType, id));
    }

    public MetadataItemNotFoundException(Integer number, String property) {
        super(String.format("Cannot find metadata item with %s %d", property, number));
    }

    public MetadataItemNotFoundException(String title) {
        super("Cannot find metadata item with title " + title);
    }
}
