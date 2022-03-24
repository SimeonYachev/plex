package com.scalefocus.java.simeonyachev.services.sqlite.impl;

import com.scalefocus.java.simeonyachev.domain.sqlite.MediaItem;
import com.scalefocus.java.simeonyachev.domain.sqlite.MediaStream;
import com.scalefocus.java.simeonyachev.exceptions.sqlite.MediaStreamNotFoundException;
import com.scalefocus.java.simeonyachev.repositories.sqlite.MediaStreamRepository;
import com.scalefocus.java.simeonyachev.services.sqlite.MediaStreamService;
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

class MediaStreamServiceImplTest {

    private static MediaStream EXPECTED_MEDIA_STREAM;

    private MediaStreamRepository mediaStreamRepositoryMock;
    private MediaStreamService mediaStreamService;

    @BeforeAll
    static void setExpectedMediaStream() {
        EXPECTED_MEDIA_STREAM = new MediaStream();
        EXPECTED_MEDIA_STREAM.setLanguage("expectedLanguage");
    }

    @BeforeEach
    void setUp() {
        mediaStreamRepositoryMock = Mockito.mock(MediaStreamRepository.class);
        mediaStreamService = new MediaStreamServiceImpl(mediaStreamRepositoryMock);
    }

    @Test
    void getMediaStreamByIdSuccessfully() {
        int anyInt = anyInt();
        when(mediaStreamRepositoryMock.findById(anyInt)).thenReturn(Optional.of(EXPECTED_MEDIA_STREAM));

        MediaStream actual = mediaStreamService.getById(anyInt);

        verify(mediaStreamRepositoryMock).findById(anyInt);

        assertEquals(EXPECTED_MEDIA_STREAM, actual, "Wrong media stream found by id.");
    }

    @Test
    void getMediaStreamByIdNotFound() {
        int anyInt = anyInt();
        when(mediaStreamRepositoryMock.findById(anyInt)).thenThrow(MediaStreamNotFoundException.class);

        assertThrows(MediaStreamNotFoundException.class, () -> mediaStreamService.getById(anyInt));
        verify(mediaStreamRepositoryMock).findById(anyInt);
    }

    @Test
    void getMediaStreamsByMediaItemSuccessfully() {
        List<MediaStream> expected = Lists.list(EXPECTED_MEDIA_STREAM);

        MediaItem anyMediaItem = any();
        when(mediaStreamRepositoryMock.findAllByMediaItem(anyMediaItem)).thenReturn(Optional.of(expected));

        Collection<MediaStream> actual = mediaStreamService.getAllByMediaItem(anyMediaItem);

        verify(mediaStreamRepositoryMock).findAllByMediaItem(anyMediaItem);

        assertEquals(expected, actual, "Media streams found by media item don't match the ones expected.");
    }

    @Test
    void getMediaStreamsByMediaItemNotFound() {
        MediaItem anyMediaItem = any();
        when(mediaStreamRepositoryMock.findAllByMediaItem(anyMediaItem)).thenThrow(MediaStreamNotFoundException.class);

        assertThrows(MediaStreamNotFoundException.class, () -> mediaStreamService.getAllByMediaItem(anyMediaItem));
        verify(mediaStreamRepositoryMock).findAllByMediaItem(anyMediaItem);
    }

    @Test
    void getAllLanguagesSuccessfully() {
        List<MediaStream> expectedMediaStreams = Lists.list(EXPECTED_MEDIA_STREAM);

        Collection<String> expectedLanguages = Lists.list(EXPECTED_MEDIA_STREAM.getLanguage());

        when(mediaStreamRepositoryMock.findAll()).thenReturn(expectedMediaStreams);

        Collection<String> actualLanguages = mediaStreamService.getAllLanguages();

        verify(mediaStreamRepositoryMock).findAll();

        assertEquals(expectedLanguages, actualLanguages, "Languages found don't match the ones expected.");
    }
}