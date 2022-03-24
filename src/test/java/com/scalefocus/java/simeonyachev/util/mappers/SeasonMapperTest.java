package com.scalefocus.java.simeonyachev.util.mappers;

import com.scalefocus.java.simeonyachev.domain.mysql.Season;
import com.scalefocus.java.simeonyachev.domain.mysql.Series;
import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;
import com.scalefocus.java.simeonyachev.services.sqlite.MetadataItemService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeasonMapperTest {

    private static MetadataItem INPUT_METADATA_ITEM;
    private static Season EXPECTED_SEASON;
    private static Series EXPECTED_SERIES;

    private SeasonMapper seasonMapper;

    @BeforeAll
    static void setExpectedInputAndOutput() {
        INPUT_METADATA_ITEM = new MetadataItem();
        EXPECTED_SERIES = new Series();
        EXPECTED_SEASON = new Season.SeasonBuilder()
                .episodes(new ArrayList<>())
                .series(EXPECTED_SERIES)
                .build();
    }

    @BeforeEach
    void setUp() {
        seasonMapper = new SeasonMapper(Mockito.mock(MetadataItemService.class), Mockito.mock(EpisodeMapper.class));
    }

    @Test
    void toSeasonSuccessful() {
        Season actualSeason = seasonMapper.toSeason(INPUT_METADATA_ITEM, EXPECTED_SERIES);

        assertEquals(EXPECTED_SEASON, actualSeason, "Mapped season doesn't match the one expected.");
    }
}
