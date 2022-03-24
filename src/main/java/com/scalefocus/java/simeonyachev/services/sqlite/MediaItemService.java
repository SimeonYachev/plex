package com.scalefocus.java.simeonyachev.services.sqlite;

import com.scalefocus.java.simeonyachev.domain.sqlite.MediaItem;
import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;

import java.util.Collection;

public interface MediaItemService {

    MediaItem getById(Integer id);

    Collection<MediaItem> getAllByMetadataItem(MetadataItem metadataItem);

    Collection<MediaItem> getAllMediaItems();
}
