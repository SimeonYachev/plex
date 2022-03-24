package com.scalefocus.java.simeonyachev.services.sqlite.impl;

import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;
import com.scalefocus.java.simeonyachev.exceptions.sqlite.MetadataItemNotFoundException;
import com.scalefocus.java.simeonyachev.repositories.sqlite.MetadataItemRepository;
import com.scalefocus.java.simeonyachev.services.sqlite.MetadataItemService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MetadataItemServiceImplTest {

    private static MetadataItem EXPECTED_METADATA_ITEM;

    private MetadataItemRepository metadataItemRepositoryMock;
    private MetadataItemService metadataItemService;

    @BeforeAll
    static void setExpectedMetadataItem() {
        EXPECTED_METADATA_ITEM = new MetadataItem();
    }

    @BeforeEach
    void setUp() {
        metadataItemRepositoryMock = Mockito.mock(MetadataItemRepository.class);
        metadataItemService = new MetadataItemServiceImpl(metadataItemRepositoryMock);
    }

    @Test
    void getMetadataItemByIdSuccessfully() {
        int anyInt = anyInt();
        when(metadataItemRepositoryMock.findById(anyInt)).thenReturn(Optional.of(EXPECTED_METADATA_ITEM));

        MetadataItem actual = metadataItemService.getById(anyInt);

        verify(metadataItemRepositoryMock).findById(anyInt);

        assertEquals(EXPECTED_METADATA_ITEM, actual, "Wrong metadata item found by id.");
    }

    @Test
    void getMetadataItemByIdNotFound() {
        int anyInt = anyInt();
        when(metadataItemRepositoryMock.findById(anyInt)).thenThrow(MetadataItemNotFoundException.class);

        assertThrows(MetadataItemNotFoundException.class, () -> metadataItemService.getById(anyInt));
        verify(metadataItemRepositoryMock).findById(anyInt);
    }

    @Test
    void getMetadataItemByTitleSuccessfully() {
        String anyString = anyString();
        when(metadataItemRepositoryMock.findByTitle(anyString)).thenReturn(Optional.of(EXPECTED_METADATA_ITEM));

        MetadataItem actual = metadataItemService.getByTitle(anyString);

        verify(metadataItemRepositoryMock).findByTitle(anyString);

        assertEquals(EXPECTED_METADATA_ITEM, actual, "Wrong metadata item found by title.");
    }

    @Test
    void getMetadataItemByTitleNotFound() {
        String anyString = anyString();
        when(metadataItemRepositoryMock.findByTitle(anyString)).thenThrow(MetadataItemNotFoundException.class);

        assertThrows(MetadataItemNotFoundException.class, () -> metadataItemService.getByTitle(anyString));
        verify(metadataItemRepositoryMock).findByTitle(anyString);
    }

    @Test
    void getAllMetadataByTypeSuccessfully() {
        List<MetadataItem> expected = Lists.list(EXPECTED_METADATA_ITEM);

        int anyInt = anyInt();
        when(metadataItemRepositoryMock.findAllByMetadataType(anyInt)).thenReturn(Optional.of(expected));

        Collection<MetadataItem> actual = metadataItemService.getAllByMetadataType(anyInt);

        verify(metadataItemRepositoryMock).findAllByMetadataType(anyInt);

        assertEquals(expected, actual, "Wrong metadata items by type found.");
    }

    @Test
    void getAllMetadataByTypeNotFound() {
        int anyInt = anyInt();
        when(metadataItemRepositoryMock.findAllByMetadataType(anyInt)).thenThrow(MetadataItemNotFoundException.class);

        assertThrows(MetadataItemNotFoundException.class, () -> metadataItemService.getAllByMetadataType(anyInt));
        verify(metadataItemRepositoryMock).findAllByMetadataType(anyInt);
    }

    @Test
    void getAllSeasonsOfSeriesSuccessfully() {
        List<MetadataItem> expected = Lists.list(EXPECTED_METADATA_ITEM);

        int anyInt = anyInt();
        when(metadataItemRepositoryMock.findAllSeasonsOfSeries(anyInt)).thenReturn(Optional.of(expected));

        Collection<MetadataItem> actual = metadataItemService.getAllSeasonsOfSeries(anyInt);

        verify(metadataItemRepositoryMock).findAllSeasonsOfSeries(anyInt);

        assertEquals(expected, actual, "Wrong seasons of series found.");
    }

    @Test
    void getAllSeasonsOfSeriesNotFound() {
        int anyInt = anyInt();
        when(metadataItemRepositoryMock.findAllSeasonsOfSeries(anyInt)).thenThrow(MetadataItemNotFoundException.class);

        assertThrows(MetadataItemNotFoundException.class, () -> metadataItemService.getAllSeasonsOfSeries(anyInt));
        verify(metadataItemRepositoryMock).findAllSeasonsOfSeries(anyInt);
    }

    @Test
    void getAllEpisodesOfSeasonSuccessfully() {
        List<MetadataItem> expected = Lists.list(EXPECTED_METADATA_ITEM);

        int anyInt = anyInt();
        when(metadataItemRepositoryMock.findAllEpisodesOfSeason(anyInt)).thenReturn(Optional.of(expected));

        Collection<MetadataItem> actual = metadataItemService.getAllEpisodesOfSeason(anyInt);

        verify(metadataItemRepositoryMock).findAllEpisodesOfSeason(anyInt);

        assertEquals(expected, actual, "Wrong episodes of season found.");
    }

    @Test
    void getAllEpisodesOfSeasonNotFound() {
        int anyInt = anyInt();
        when(metadataItemRepositoryMock.findAllEpisodesOfSeason(anyInt)).thenThrow(MetadataItemNotFoundException.class);

        assertThrows(MetadataItemNotFoundException.class, () -> metadataItemService.getAllEpisodesOfSeason(anyInt));
        verify(metadataItemRepositoryMock).findAllEpisodesOfSeason(anyInt);
    }
}