package com.scalefocus.java.simeonyachev.util.mappers;

import com.scalefocus.java.simeonyachev.domain.mysql.Episode;
import com.scalefocus.java.simeonyachev.domain.mysql.Season;
import com.scalefocus.java.simeonyachev.domain.mysql.Series;
import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;
import com.scalefocus.java.simeonyachev.services.sqlite.MetadataItemService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class SeasonMapper {

    private final MetadataItemService metadataItemService;
    private final EpisodeMapper episodeMapper;

    public SeasonMapper(MetadataItemService metadataItemService, EpisodeMapper episodeMapper) {
        this.metadataItemService = metadataItemService;
        this.episodeMapper = episodeMapper;
    }

    public Season toSeason(MetadataItem metadataSeason, Series series) {

        Season season = new Season.SeasonBuilder()
                .id(metadataSeason.getId())
                .number(metadataSeason.getIndex())
                .series(series)
                .build();

        season.setEpisodes(mapEpisodes(metadataItemService.getAllEpisodesOfSeason(metadataSeason.getId()), season));
        return season;
    }

    private Collection<Episode> mapEpisodes(Collection<MetadataItem> metadataEpisodes, Season season) {
        return  metadataEpisodes.stream()
                .map(metadataEpisode -> episodeMapper.toEpisode(metadataEpisode, season))
                .collect(Collectors.toList());
    }
}
