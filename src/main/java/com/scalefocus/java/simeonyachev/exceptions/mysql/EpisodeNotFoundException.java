package com.scalefocus.java.simeonyachev.exceptions.mysql;

import com.scalefocus.java.simeonyachev.exceptions.ResourceNotFoundException;

public class EpisodeNotFoundException extends ResourceNotFoundException {

    public EpisodeNotFoundException(Integer id) {
        super("Cannot find episode with id " + id);
    }
}
