package com.scalefocus.java.simeonyachev.services.sqlite;

import com.scalefocus.java.simeonyachev.domain.sqlite.StreamType;

import java.util.Collection;

public interface StreamTypeService {

    StreamType getById(Integer id);

    Collection<StreamType> getStreamTypes();
}
