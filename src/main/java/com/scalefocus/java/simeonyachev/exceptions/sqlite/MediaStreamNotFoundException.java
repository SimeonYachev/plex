package com.scalefocus.java.simeonyachev.exceptions.sqlite;

import com.scalefocus.java.simeonyachev.domain.sqlite.MediaItem;
import com.scalefocus.java.simeonyachev.exceptions.ResourceNotFoundException;

public class MediaStreamNotFoundException extends ResourceNotFoundException {

    public MediaStreamNotFoundException(Integer id) {
        super("Cannot find media stream with id " + id);
    }

    public MediaStreamNotFoundException(MediaItem mediaItem) {
        super("Cannot find media stream with media item " + mediaItem);
    }
}
