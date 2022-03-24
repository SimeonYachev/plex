package com.scalefocus.java.simeonyachev.services.sqlite.impl;

import com.scalefocus.java.simeonyachev.domain.sqlite.MediaItem;
import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;
import com.scalefocus.java.simeonyachev.exceptions.sqlite.MediaItemNotFoundException;
import com.scalefocus.java.simeonyachev.repositories.sqlite.MediaItemRepository;
import com.scalefocus.java.simeonyachev.services.sqlite.MediaItemService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MediaItemServiceImplTest {

    private static MediaItem EXPECTED_MEDIA_ITEM;

    private MediaItemRepository mediaItemRepositoryMock;
    private MediaItemService mediaItemService;

    @BeforeAll
    static void setExpectedMediaItem() {
        EXPECTED_MEDIA_ITEM = new MediaItem();
    }

    @BeforeEach
    void setUp() {
        mediaItemRepositoryMock = Mockito.mock(MediaItemRepository.class);
        mediaItemService = new MediaItemServiceImpl(mediaItemRepositoryMock);
    }

    @Test
    void getMediaItemByIdSuccessfully() {
        int anyInt = anyInt();
        when(mediaItemRepositoryMock.findById(anyInt)).thenReturn(Optional.of(EXPECTED_MEDIA_ITEM));

        MediaItem actual = mediaItemService.getById(anyInt);

        verify(mediaItemRepositoryMock).findById(anyInt);

        assertEquals(EXPECTED_MEDIA_ITEM, actual, "Wrong media item found by id ");
    }

    @Test
    void getMediaItemByIdNotFound() {
        int anyInt = anyInt();
        when(mediaItemRepositoryMock.findById(anyInt)).thenThrow(MediaItemNotFoundException.class);

        assertThrows(MediaItemNotFoundException.class, () -> mediaItemService.getById(anyInt));
        verify(mediaItemRepositoryMock).findById(anyInt);
    }

    @Test
    void getAllByMetadataItemSuccessfully() {
        List<MediaItem> expected = Lists.list(EXPECTED_MEDIA_ITEM);

        MetadataItem anyMetadataItem = any();
        when(mediaItemRepositoryMock.findAllByMetadataItem(anyMetadataItem)).thenReturn(Optional.of(expected));

        Collection<MediaItem> actual = mediaItemService.getAllByMetadataItem(anyMetadataItem);

        verify(mediaItemRepositoryMock).findAllByMetadataItem(anyMetadataItem);

        assertEquals(expected, actual, "Media items found by metadata item don't match the ones expected.");
    }

    @Test
    void getAllByMetadataItemNotFound() {
        MetadataItem anyMetadataItem = any();
        when(mediaItemRepositoryMock.findAllByMetadataItem(anyMetadataItem)).thenThrow(MediaItemNotFoundException.class);

        assertThrows(MediaItemNotFoundException.class, () -> mediaItemService.getAllByMetadataItem(anyMetadataItem));
        verify(mediaItemRepositoryMock).findAllByMetadataItem(anyMetadataItem);
    }

    @Test
    void getAllMediaItemsSuccessfully() {
        List<MediaItem> expected = Lists.list(EXPECTED_MEDIA_ITEM);

        when(mediaItemRepositoryMock.findAll()).thenReturn(expected);

        Collection<MediaItem> actual = mediaItemService.getAllMediaItems();

        verify(mediaItemRepositoryMock).findAll();

        assertEquals(expected, actual, "Wrong media items found.");
    }
}