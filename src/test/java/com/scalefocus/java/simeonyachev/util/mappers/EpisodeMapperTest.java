package com.scalefocus.java.simeonyachev.util.mappers;

import com.scalefocus.java.simeonyachev.domain.mysql.Episode;
import com.scalefocus.java.simeonyachev.domain.mysql.Season;
import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;
import com.scalefocus.java.simeonyachev.util.MetadataItemUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class EpisodeMapperTest {

    private static MetadataItem INPUT_METADATA_ITEM;
    private static Episode EXPECTED_EPISODE;
    private static Season EXPECTED_SEASON;

    private EpisodeMapper episodeMapper;

    @BeforeAll
    static void setExpectedInputAndOutput() {
        INPUT_METADATA_ITEM = new MetadataItem();
        EXPECTED_SEASON = new Season();
        EXPECTED_EPISODE = new Episode.EpisodeBuilder()
                .year(0)
                .duration(0)
                .season(EXPECTED_SEASON)
                .build();
    }

    @BeforeEach
    void setUp() {
        episodeMapper = new EpisodeMapper(Mockito.mock(MetadataItemUtils.class));
    }

    @Test
    void toEpisodeSuccessful() {
        Episode actualEpisode = episodeMapper.toEpisode(INPUT_METADATA_ITEM, EXPECTED_SEASON);

        assertEquals(EXPECTED_EPISODE, actualEpisode, "Mapped episode doesn't match the one expected.");
    }
}