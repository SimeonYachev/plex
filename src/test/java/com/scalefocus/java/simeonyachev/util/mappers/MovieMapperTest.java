package com.scalefocus.java.simeonyachev.util.mappers;

import com.scalefocus.java.simeonyachev.domain.mysql.Movie;
import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;
import com.scalefocus.java.simeonyachev.util.MetadataItemUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MovieMapperTest {

    private static final String RELEASE_DATE = "1999-12-12 12:12:12";
    private static MetadataItem INPUT_METADATA_ITEM;
    private static Movie EXPECTED_MOVIE;

    private MovieMapper movieMapper;

    @BeforeAll
    static void setExpectedInputAndOutput() {
        INPUT_METADATA_ITEM = new MetadataItem();
        INPUT_METADATA_ITEM.setOriginallyAvailableAt(RELEASE_DATE);
        EXPECTED_MOVIE = new Movie.MovieBuilder()
                .releaseDate(RELEASE_DATE)
                .year(0)
                .duration(0)
                .build();
    }

    @BeforeEach
    void setUp() {
        movieMapper = new MovieMapper(mock(MetadataItemUtils.class));
    }

    @Test
    void toMovieSuccessful() {
        Movie actualMovie = movieMapper.toMovie(INPUT_METADATA_ITEM);

        assertEquals(EXPECTED_MOVIE, actualMovie, "Mapped movie doesn't match the one expected.");
    }
}