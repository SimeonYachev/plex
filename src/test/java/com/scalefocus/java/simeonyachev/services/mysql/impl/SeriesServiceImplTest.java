package com.scalefocus.java.simeonyachev.services.mysql.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.java.simeonyachev.domain.mysql.Episode;
import com.scalefocus.java.simeonyachev.domain.mysql.Season;
import com.scalefocus.java.simeonyachev.domain.mysql.Series;
import com.scalefocus.java.simeonyachev.exceptions.mysql.SeriesNotFoundException;
import com.scalefocus.java.simeonyachev.repositories.mysql.SeriesRepository;
import com.scalefocus.java.simeonyachev.services.mysql.SeriesService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class SeriesServiceImplTest {

    private static final String OMDB_API_KEY = "12f5884f";
    private static final String GET_SERIES_INFO_BY_TITLE_URL = "http://www.omdbapi.com/?apikey=" + OMDB_API_KEY + "&t=";
    private static final String EXPECTED_JSON_REPLY = "{\"imdbID\":\"expectedImdbId\"}";


    private static Episode EXPECTED_EPISODE;
    private static Season EXPECTED_SEASON;
    private static Series EXPECTED_SERIES;
    private static String FINAL_SERIES_URL;
    private static String FINAL_EPISODE_URL;

    private SeriesRepository seriesRepositoryMock;
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private SeriesService seriesService;

    @BeforeAll
    static void setExpectedSeries() {
        EXPECTED_EPISODE = new Episode.EpisodeBuilder()
                .number(1)
                .imdbId("expectedImdbId")
                .build();
        EXPECTED_SEASON = new Season.SeasonBuilder()
                .number(1)
                .episodes(Lists.list(EXPECTED_EPISODE))
                .build();
        EXPECTED_SERIES = new Series.SeriesBuilder()
                .title("expectedTitle")
                .imdbId("expectedImdbId")
                .seasons(Lists.list(EXPECTED_SEASON))
                .build();
        FINAL_SERIES_URL = GET_SERIES_INFO_BY_TITLE_URL + EXPECTED_SERIES.getTitle().replace(" ", "+");
        FINAL_EPISODE_URL = FINAL_SERIES_URL
                + "&season=" + EXPECTED_SEASON.getNumber()
                + "&episode=" + EXPECTED_EPISODE.getNumber();
    }

    @BeforeEach
    void setUp() {
        seriesRepositoryMock = mock(SeriesRepository.class);
        restTemplate = mock(RestTemplate.class);
        objectMapper = mock(ObjectMapper.class);
        seriesService = new SeriesServiceImpl(seriesRepositoryMock, restTemplate, objectMapper);
    }

    @Test
    void getSeriesByIdSuccessfully() {
        int anyInt = anyInt();
        when(seriesRepositoryMock.findById(anyInt)).thenReturn(Optional.of(EXPECTED_SERIES));
        when(restTemplate.getForEntity(FINAL_SERIES_URL, String.class)).thenReturn(ResponseEntity.ok(EXPECTED_JSON_REPLY));
        when(restTemplate.getForEntity(FINAL_EPISODE_URL, String.class)).thenReturn(ResponseEntity.ok(EXPECTED_JSON_REPLY));

        Series actualSeries = seriesService.getById(anyInt);

        verify(seriesRepositoryMock).findById(anyInt);

        assertEquals(EXPECTED_SERIES, actualSeries, "Wrong series found by id");
    }

    @Test
    void getSeriesByIdNotFound() {
        int anyInt = anyInt();
        when(seriesRepositoryMock.findById(anyInt)).thenThrow(SeriesNotFoundException.class);

        assertThrows(SeriesNotFoundException.class, () -> seriesService.getById(anyInt));
        verify(seriesRepositoryMock).findById(anyInt);
    }

    @Test
    void saveSeriesSuccessfully() {
        Series anySeries = any();
        when(seriesRepositoryMock.save(anySeries)).thenReturn(EXPECTED_SERIES);

        Series actualSeries = seriesService.saveSeries(anySeries);

        verify(seriesRepositoryMock).save(anySeries);

        assertNotNull(actualSeries);
        assertEquals(EXPECTED_SERIES, actualSeries, "Saved series don't match the ones expected.");
    }

    @Test
    void saveAllSeriesSuccessfully() {
        List<Series> expectedSeries = Lists.list(EXPECTED_SERIES);

        Collection<Series> anyCollection = anyCollection();
        when(seriesRepositoryMock.saveAll(anyCollection)).thenReturn(expectedSeries);

        Collection<Series> actualSeries = seriesService.saveSeries(anyCollection);

        verify(seriesRepositoryMock).saveAll(anyCollection);

        assertEquals(expectedSeries, actualSeries, "Saved collection of series doesn't match the one expected.");
    }
}