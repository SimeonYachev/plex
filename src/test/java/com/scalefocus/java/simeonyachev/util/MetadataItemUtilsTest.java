package com.scalefocus.java.simeonyachev.util;

import com.scalefocus.java.simeonyachev.domain.sqlite.MediaItem;
import com.scalefocus.java.simeonyachev.domain.sqlite.MediaStream;
import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;
import com.scalefocus.java.simeonyachev.domain.sqlite.StreamType;
import com.scalefocus.java.simeonyachev.exceptions.sqlite.MediaItemNotFoundException;
import com.scalefocus.java.simeonyachev.exceptions.sqlite.MediaStreamNotFoundException;
import com.scalefocus.java.simeonyachev.services.sqlite.MediaItemService;
import com.scalefocus.java.simeonyachev.services.sqlite.MediaStreamService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.collections.Iterables;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MetadataItemUtilsTest {

    private static final int AUDIO_ID = 1;

    private MediaItem expectedMediaItem;
    private MediaItemService mediaItemServiceMock;
    private MediaStreamService mediaStreamServiceMock;
    private MetadataItemUtils metadataItemUtils;

    @BeforeEach
    void setUp() {
        mediaItemServiceMock = mock(MediaItemService.class);
        mediaStreamServiceMock = mock(MediaStreamService.class);
        metadataItemUtils = new MetadataItemUtils(mediaItemServiceMock, mediaStreamServiceMock);
        expectedMediaItem = new MediaItem();
    }

    @Test
    void extractDurationSuccessfully() {
        expectedMediaItem.setDuration(1234);
        Collection<MediaItem> expectedMediaItems = Lists.list(expectedMediaItem);

        MetadataItem metadataItem = new MetadataItem();
        when(mediaItemServiceMock.getAllByMetadataItem(metadataItem)).thenReturn(expectedMediaItems);

        Integer expectedDuration = 1234;
        Integer actualDuration = metadataItemUtils.extractDuration(metadataItem);

        assertEquals(expectedDuration, actualDuration, "Duration doesn't match the one expected.");
    }

    @Test
    void extractDurationNull() {
        Collection<MediaItem> expectedMediaItems = Lists.list(expectedMediaItem);

        MetadataItem anyMetadataItem = any(MetadataItem.class);
        when(mediaItemServiceMock.getAllByMetadataItem(anyMetadataItem)).thenReturn(expectedMediaItems);

        Integer expectedDuration = null;
        Integer actualDuration = metadataItemUtils.extractDuration(anyMetadataItem);

        assertEquals(expectedDuration, actualDuration, "Duration doesn't match the one expected.");
    }

    @Test
    void extractDurationMediaItemNotFound() {
        when(mediaItemServiceMock.getAllByMetadataItem(any())).thenThrow(MediaItemNotFoundException.class);

        MetadataItem metadataItem = new MetadataItem();
        assertThrows(MediaItemNotFoundException.class, () -> metadataItemUtils.extractDuration(metadataItem));
        verify(mediaItemServiceMock).getAllByMetadataItem(any());
    }

    @Test
    void extractImdbIdSuccessfully() {
        MetadataItem metadataItem = new MetadataItem();
        metadataItem.setGuid("bla_imdb_tt123456?_bla");

        String expected = "tt123456";
        String actual = metadataItemUtils.extractImdbId(metadataItem);

        assertEquals(expected, actual, "The imdb id doesn't match the one expected.");
    }

    @Test
    void extractImdbIdGuidNull() {
        MetadataItem metadataItem = new MetadataItem();

        String expected = null;
        String actual = metadataItemUtils.extractImdbId(metadataItem);

        assertEquals(expected, actual, "This metadata item is expected not to have an IMDB id.");
    }

    @Test
    void extractImdbIdGuidNotContainingImdbId() {
        MetadataItem metadataItem = new MetadataItem();
        metadataItem.setGuid("no_I_m_d_b_Id");

        String expected = null;
        String actual = metadataItemUtils.extractImdbId(metadataItem);

        assertEquals(expected, actual, "This metadata item is expected not to have an IMDB id.");
    }

    @Test
    void extractYearSuccessfully() {
        MetadataItem metadataItem = new MetadataItem();
        metadataItem.setOriginallyAvailableAt("2000-12-12 12:12:12");

        Integer expected = 2000;
        Integer actual = metadataItemUtils.extractYear(metadataItem);

        assertEquals(expected, actual, "The release year doesn't match the one expected.");
    }

    @Test
    void extractYearOriginallyAvailableAtNull() {
        MetadataItem metadataItem = new MetadataItem();

        Integer expected = null;
        Integer actual = metadataItemUtils.extractYear(metadataItem);

        assertEquals(expected, actual, "This metadata item is expected not to have a release year.");
    }

    @Test
    void extractLanguageSuccessfully() {
        MetadataItem metadataItem = new MetadataItem();

        Collection<MediaItem> expectedMediaItems = Lists.list(expectedMediaItem);
        when(mediaItemServiceMock.getAllByMetadataItem(metadataItem)).thenReturn(expectedMediaItems);

        MediaStream expectedMediaStream = new MediaStream();
        expectedMediaStream.setStreamType(new StreamType(AUDIO_ID, "audio"));
        expectedMediaStream.setLanguage("bul");
        Collection<MediaStream> expectedMediaStreams = Lists.list(expectedMediaStream);
        when(mediaStreamServiceMock.getAllByMediaItem(Iterables.firstOf(expectedMediaItems))).thenReturn(expectedMediaStreams);

        String expectedLanguage = "[bul]";
        String actualLanguage = metadataItemUtils.extractLanguages(metadataItem, AUDIO_ID);

        assertEquals(expectedLanguage, actualLanguage, "The language doesn't match the one expected.");
    }

    @Test
    void extractManyLanguagesSuccessfully() {
        MetadataItem metadataItem = new MetadataItem();

        Collection<MediaItem> expectedMediaItems = Lists.list(expectedMediaItem);
        when(mediaItemServiceMock.getAllByMetadataItem(metadataItem)).thenReturn(expectedMediaItems);

        MediaStream expectedMediaStream1 = new MediaStream();
        expectedMediaStream1.setStreamType(new StreamType(AUDIO_ID, "audio"));
        expectedMediaStream1.setLanguage("bul");
        MediaStream expectedMediaStream2 = new MediaStream();
        expectedMediaStream2.setStreamType(new StreamType(AUDIO_ID, "audio"));
        expectedMediaStream2.setLanguage("eng");

        Collection<MediaStream> expectedMediaStreams = Lists.list(expectedMediaStream1, expectedMediaStream2);
        when(mediaStreamServiceMock.getAllByMediaItem(Iterables.firstOf(expectedMediaItems))).thenReturn(expectedMediaStreams);

        String expectedLanguage = "[bul, eng]";
        String actualLanguage = metadataItemUtils.extractLanguages(metadataItem, AUDIO_ID);

        assertEquals(expectedLanguage, actualLanguage, "The languages don't match the ones expected.");
    }

    @Test
    void extractLanguagesMediaItemNotFound() {
        when(mediaItemServiceMock.getAllByMetadataItem(any())).thenThrow(MediaItemNotFoundException.class);

        MetadataItem metadataItem = new MetadataItem();
        assertThrows(MediaItemNotFoundException.class, () -> metadataItemUtils.extractLanguages(metadataItem, anyInt()));

        verify(mediaItemServiceMock).getAllByMetadataItem(any());
    }

    @Test
    void extractLanguagesMediaStreamNotFound() {
        Collection<MediaItem> expectedMediaItems = Lists.list(expectedMediaItem);
        when(mediaItemServiceMock.getAllByMetadataItem(any())).thenReturn(expectedMediaItems);
        when(mediaStreamServiceMock.getAllByMediaItem(Iterables.firstOf(expectedMediaItems))).thenThrow(MediaStreamNotFoundException.class);

        MetadataItem metadataItem = new MetadataItem();
        assertThrows(MediaStreamNotFoundException.class, () -> metadataItemUtils.extractLanguages(metadataItem, 1));

        verify(mediaStreamServiceMock).getAllByMediaItem(any());
    }

    @Test
    void extractDistinctInfoSuccessfully() {
        String inputInfo = "director1|director2|director3|director3";

        String expected = "[director1, director2, director3]";
        String actual = metadataItemUtils.extractDistinctInfo(inputInfo);

        assertEquals(expected, actual, "The info doesn't match the one expected");
    }
}