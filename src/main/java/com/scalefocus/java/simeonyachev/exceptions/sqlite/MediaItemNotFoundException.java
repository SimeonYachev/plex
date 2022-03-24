package com.scalefocus.java.simeonyachev.exceptions.sqlite;

import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;
import com.scalefocus.java.simeonyachev.exceptions.ResourceNotFoundException;

public class MediaItemNotFoundException extends ResourceNotFoundException {

    public MediaItemNotFoundException(Integer id) {
        super("Cannot find media item with id " + id);
    }

    public MediaItemNotFoundException(MetadataItem metadataItem) {
        super("Cannot find media item with metadata item " + metadataItem);
    }
}
