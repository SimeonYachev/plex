package com.scalefocus.java.simeonyachev.util.mappers;

import com.scalefocus.java.simeonyachev.domain.mysql.Movie;
import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;
import com.scalefocus.java.simeonyachev.util.MetadataItemUtils;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    private static final Integer AUDIO_ID = 1;
    private static final Integer SUBS_ID = 3;

    private final MetadataItemUtils metadataItemUtils;

    public MovieMapper(MetadataItemUtils metadataItemUtils) {
        this.metadataItemUtils = metadataItemUtils;
    }

    public Movie toMovie(MetadataItem metadataMovie) {

        return new Movie.MovieBuilder()
                .id(metadataMovie.getId())
                .title(metadataMovie.getTitle())
                .description(metadataMovie.getSummary())
                .rating(metadataMovie.getRating())
                .directors(metadataItemUtils.extractDistinctInfo(metadataMovie.getTagsDirector()))
                .writers(metadataItemUtils.extractDistinctInfo(metadataMovie.getTagsWriter()))
                .duration(metadataItemUtils.extractDuration(metadataMovie))
                .year(metadataItemUtils.extractYear(metadataMovie))
                .genres(metadataItemUtils.extractDistinctInfo(metadataMovie.getTagsGenre()))
                .audio(metadataItemUtils.extractLanguages(metadataMovie, AUDIO_ID))
                .subtitles(metadataItemUtils.extractLanguages(metadataMovie, SUBS_ID))
                .releaseDate(metadataMovie.getOriginallyAvailableAt())
                .imdbId(metadataItemUtils.extractImdbId(metadataMovie))
                .stars(metadataItemUtils.extractDistinctInfo(metadataMovie.getTagsStar()))
                .build();
    }
}
