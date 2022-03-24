package com.scalefocus.java.simeonyachev.services.mysql.impl;

import com.scalefocus.java.simeonyachev.domain.mysql.Episode;
import com.scalefocus.java.simeonyachev.exceptions.mysql.EpisodeNotFoundException;
import com.scalefocus.java.simeonyachev.repositories.mysql.EpisodeRepository;
import com.scalefocus.java.simeonyachev.services.mysql.EpisodeService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EpisodeServiceImplTest {

    private static Episode EXPECTED_EPISODE;

    private EpisodeRepository episodeRepositoryMock;
    private EpisodeService episodeService;

    @BeforeAll
    static void setExpectedEpisode() {
        EXPECTED_EPISODE = new Episode.EpisodeBuilder()
                .title("expectedTitle")
                .build();
    }

    @BeforeEach
    void setUp() {
        episodeRepositoryMock = mock(EpisodeRepository.class);
        episodeService = new EpisodeServiceImpl(episodeRepositoryMock);
    }

    @Test
    void getEpisodeByIdSuccessfully() {
        int anyInt = anyInt();

        when(episodeRepositoryMock.findById(anyInt)).thenReturn(Optional.of(EXPECTED_EPISODE));

        Episode actualEpisode = episodeService.getById(anyInt);

        verify(episodeRepositoryMock).findById(anyInt);

        assertNotNull(actualEpisode);
        assertEquals(EXPECTED_EPISODE, actualEpisode, "Wrong episode found by id.");
    }

    @Test
    void getEpisodeByIdNotFound() {
        int anyInt = anyInt();
        when(episodeRepositoryMock.findById(anyInt)).thenThrow(EpisodeNotFoundException.class);

        assertThrows(EpisodeNotFoundException.class, () -> episodeService.getById(anyInt));
        verify(episodeRepositoryMock).findById(anyInt);
    }

    @Test
    void saveEpisodeSuccessfully() {
        Episode anyEpisode = any();
        when(episodeRepositoryMock.save(anyEpisode)).thenReturn(EXPECTED_EPISODE);

        Episode actualEpisode = episodeService.saveEpisode(anyEpisode);

        verify(episodeRepositoryMock).save(anyEpisode);

        assertNotNull(actualEpisode);
        assertEquals(EXPECTED_EPISODE, actualEpisode, "Saved episode does not match the one expected.");
    }

    @Test
    void saveEpisodesSuccessfully() {
        List<Episode> expectedEpisodes = Lists.list(EXPECTED_EPISODE);

        Collection<Episode> anyCollection = anyCollection();
        when(episodeRepositoryMock.saveAll(anyCollection)).thenReturn(expectedEpisodes);

        Collection<Episode> actualEpisodes = episodeService.saveEpisodes(anyCollection);

        verify(episodeRepositoryMock).saveAll(anyCollection);

        assertEquals(expectedEpisodes, actualEpisodes, "Saved episodes do not match the ones expected.");
    }
}