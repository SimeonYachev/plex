package com.scalefocus.java.simeonyachev.util.mappers;

import com.scalefocus.java.simeonyachev.domain.mysql.Episode;
import com.scalefocus.java.simeonyachev.domain.mysql.Season;
import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;
import com.scalefocus.java.simeonyachev.util.MetadataItemUtils;
import org.springframework.stereotype.Component;

@Component
public class EpisodeMapper {

    private static final Integer AUDIO_ID = 1;
    private static final Integer SUBS_ID = 3;

    private final MetadataItemUtils metadataItemUtils;

    public EpisodeMapper(MetadataItemUtils metadataItemUtils) {
        this.metadataItemUtils = metadataItemUtils;
    }

    public Episode toEpisode(MetadataItem metadataEpisode, Season season) {

        return new Episode.EpisodeBuilder()
                .id(metadataEpisode.getId())
                .title(metadataEpisode.getTitle())
                .description(metadataEpisode.getSummary())
                .rating(metadataEpisode.getRating())
                .releaseDate(metadataEpisode.getOriginallyAvailableAt())
                .directors(metadataItemUtils.extractDistinctInfo(metadataEpisode.getTagsDirector()))
                .writers(metadataItemUtils.extractDistinctInfo(metadataEpisode.getTagsWriter()))
                .duration(metadataItemUtils.extractDuration(metadataEpisode))
                .imdbId(metadataItemUtils.extractImdbId(metadataEpisode))
                .year(metadataItemUtils.extractYear(metadataEpisode))
                .number(metadataEpisode.getIndex())
                .audio(metadataItemUtils.extractLanguages(metadataEpisode, AUDIO_ID))
                .subtitles(metadataItemUtils.extractLanguages(metadataEpisode, SUBS_ID))
                .stars(metadataItemUtils.extractDistinctInfo(metadataEpisode.getTagsStar()))
                .season(season)
                .build();
    }
}
