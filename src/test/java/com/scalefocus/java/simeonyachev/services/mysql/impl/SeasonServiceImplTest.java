package com.scalefocus.java.simeonyachev.services.mysql.impl;

import com.scalefocus.java.simeonyachev.domain.mysql.Season;
import com.scalefocus.java.simeonyachev.exceptions.mysql.SeasonNotFoundException;
import com.scalefocus.java.simeonyachev.repositories.mysql.SeasonRepository;
import com.scalefocus.java.simeonyachev.services.mysql.SeasonService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SeasonServiceImplTest {

    private static Season EXPECTED_SEASON;

    private SeasonRepository seasonRepositoryMock;
    private SeasonService seasonService;

    @BeforeAll
    static void setExpectedSeason() {
        EXPECTED_SEASON = new Season();
    }

    @BeforeEach
    void setUp() {
        seasonRepositoryMock = Mockito.mock(SeasonRepository.class);
        seasonService = new SeasonServiceImpl(seasonRepositoryMock);
    }

    @Test
    void getSeasonByIdSuccessfully() {
        int anyInt = anyInt();
        when(seasonRepositoryMock.findById(anyInt)).thenReturn(Optional.of(EXPECTED_SEASON));

        Season actualSeason = seasonService.getById(anyInt);

        verify(seasonRepositoryMock).findById(anyInt);

        assertEquals(EXPECTED_SEASON, actualSeason, "Wrong season found by id");
    }

    @Test
    void getSeasonByIdNotFound() {
        int anyInt = anyInt();
        when(seasonRepositoryMock.findById(anyInt)).thenThrow(SeasonNotFoundException.class);

        assertThrows(SeasonNotFoundException.class, () -> seasonService.getById(anyInt));
        verify(seasonRepositoryMock).findById(anyInt);
    }

    @Test
    void saveSeasonSuccessfully() {
        Season anySeason = any();
        when(seasonRepositoryMock.save(anySeason)).thenReturn(EXPECTED_SEASON);

        Season actualSeason = seasonService.saveSeason(anySeason);

        verify(seasonRepositoryMock).save(anySeason);

        assertNotNull(actualSeason);
        assertEquals(EXPECTED_SEASON, actualSeason, "Saved season does not match the one expected.");
    }

    @Test
    void saveSeasonsSuccessfully() {
        List<Season> expectedSeasons = Lists.list(EXPECTED_SEASON);

        Collection<Season> anyCollection = anyCollection();
        when(seasonRepositoryMock.saveAll(anyCollection)).thenReturn(expectedSeasons);

        Collection<Season> actualSeasons = seasonService.saveSeasons(anyCollection);

        verify(seasonRepositoryMock).saveAll(anyCollection);

        assertEquals(expectedSeasons, actualSeasons, "Saved seasons don't match the ones expected.");
    }
}