package com.scalefocus.java.simeonyachev.services.sqlite;

import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;

import java.util.Collection;

public interface MetadataItemService {

    MetadataItem getById(Integer id);

    MetadataItem getByTitle(String title);

    Collection<MetadataItem> getAllByMetadataType(Integer type);

    Collection<MetadataItem> getAllSeasonsOfSeries(Integer seriesId);

    Collection<MetadataItem> getAllEpisodesOfSeason(Integer seasonId);
}
