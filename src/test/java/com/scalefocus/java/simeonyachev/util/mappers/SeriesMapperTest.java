package com.scalefocus.java.simeonyachev.util.mappers;

import com.scalefocus.java.simeonyachev.domain.mysql.Series;
import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;
import com.scalefocus.java.simeonyachev.services.sqlite.MetadataItemService;
import com.scalefocus.java.simeonyachev.util.MetadataItemUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class SeriesMapperTest {

    private static MetadataItem INPUT_METADATA_ITEM;
    private static Series EXPECTED_SERIES;

    private SeriesMapper seriesMapper;

    @BeforeAll
    static void setExpectedInputAndOutput() {
        INPUT_METADATA_ITEM = new MetadataItem();
        EXPECTED_SERIES = new Series.SeriesBuilder()
                .year(0)
                .duration(0)
                .seasons(new ArrayList<>())
                .build();
    }

    @BeforeEach
    void setUp() {
        seriesMapper = new SeriesMapper(mock(MetadataItemService.class),
                mock(MetadataItemUtils.class), mock(SeasonMapper.class));
    }

    @Test
    void toSeriesSuccessful() {
        Series actualSeries = seriesMapper.toSeries(INPUT_METADATA_ITEM);

        assertEquals(EXPECTED_SERIES, actualSeries, "Mapped series doesn't match the one expected.");
    }
}
