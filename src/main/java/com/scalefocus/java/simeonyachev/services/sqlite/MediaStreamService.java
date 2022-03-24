package com.scalefocus.java.simeonyachev.services.sqlite;

import com.scalefocus.java.simeonyachev.domain.sqlite.MediaItem;
import com.scalefocus.java.simeonyachev.domain.sqlite.MediaStream;

import java.util.Collection;

public interface MediaStreamService {

    MediaStream getById(Integer id);

    Collection<MediaStream> getAllByMediaItem(MediaItem mediaItem);

    Collection<String> getAllLanguages();
}
