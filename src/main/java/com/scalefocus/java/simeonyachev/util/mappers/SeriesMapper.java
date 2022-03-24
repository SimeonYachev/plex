package com.scalefocus.java.simeonyachev.util.mappers;

import com.scalefocus.java.simeonyachev.domain.mysql.Season;
import com.scalefocus.java.simeonyachev.domain.mysql.Series;
import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;
import com.scalefocus.java.simeonyachev.services.sqlite.MetadataItemService;
import com.scalefocus.java.simeonyachev.util.MetadataItemUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class SeriesMapper {

    private final MetadataItemService metadataItemService;
    private final MetadataItemUtils metadataItemUtils;
    private final SeasonMapper seasonMapper;

    public SeriesMapper(MetadataItemService metadataItemService, MetadataItemUtils metadataItemUtils,
                        SeasonMapper seasonMapper) {
        this.metadataItemService = metadataItemService;
        this.metadataItemUtils = metadataItemUtils;
        this.seasonMapper = seasonMapper;
    }

    public Series toSeries(MetadataItem metadataSeries) {

        Series series = new Series.SeriesBuilder()
                .id(metadataSeries.getId())
                .title(metadataSeries.getTitle())
                .description(metadataSeries.getSummary())
                .rating(metadataSeries.getRating())
                .releaseDate(metadataSeries.getOriginallyAvailableAt())
                .directors(metadataItemUtils.extractDistinctInfo(metadataSeries.getTagsDirector()))
                .writers(metadataItemUtils.extractDistinctInfo(metadataSeries.getTagsWriter()))
                .duration(metadataItemUtils.extractDuration(metadataSeries))
                .year(metadataItemUtils.extractYear(metadataSeries))
                .genres(metadataItemUtils.extractDistinctInfo(metadataSeries.getTagsGenre()))
                .imdbId(metadataItemUtils.extractImdbId(metadataSeries))
                .stars(metadataItemUtils.extractDistinctInfo(metadataSeries.getTagsStar()))
                .build();

        series.setSeasons(mapSeasons(metadataItemService.getAllSeasonsOfSeries(metadataSeries.getId()), series));
        return series;
    }

    private Collection<Season> mapSeasons(Collection<MetadataItem> metadataSeasons, Series series) {
        return metadataSeasons.stream()
                    .map(metadataSeason -> seasonMapper.toSeason(metadataSeason, series))
                    .collect(Collectors.toList());
    }
}
