package com.scalefocus.java.simeonyachev.services.sqlite.impl;

import com.scalefocus.java.simeonyachev.domain.sqlite.StreamType;
import com.scalefocus.java.simeonyachev.exceptions.sqlite.StreamTypeNotFoundException;
import com.scalefocus.java.simeonyachev.repositories.sqlite.StreamTypeRepository;
import com.scalefocus.java.simeonyachev.services.sqlite.StreamTypeService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StreamTypeServiceImplTest {

    private static StreamType EXPECTED_STREAM_TYPE;

    private StreamTypeRepository streamTypeRepositoryMock;
    private StreamTypeService streamTypeService;

    @BeforeAll
    static void setExpectedStreamType() {
        EXPECTED_STREAM_TYPE = new StreamType();
    }

    @BeforeEach
    void setUp() {
        streamTypeRepositoryMock = Mockito.mock(StreamTypeRepository.class);
        streamTypeService = new StreamTypeServiceImpl(streamTypeRepositoryMock);
    }

    @Test
    void getStreamTypeByIdSuccessfully() {
        int anyInt = anyInt();
        when(streamTypeRepositoryMock.findById(anyInt)).thenReturn(Optional.of(EXPECTED_STREAM_TYPE));

        StreamType actual = streamTypeService.getById(anyInt);

        verify(streamTypeRepositoryMock).findById(anyInt);

        assertEquals(EXPECTED_STREAM_TYPE, actual, "Wrong stream type found by id.");
    }

    @Test
    void getStreamTypeByIdNotFound() {
        int anyInt = anyInt();
        when(streamTypeRepositoryMock.findById(anyInt)).thenThrow(StreamTypeNotFoundException.class);

        assertThrows(StreamTypeNotFoundException.class, () -> streamTypeService.getById(anyInt));
        verify(streamTypeRepositoryMock).findById(anyInt);
    }

    @Test
    void getStreamTypesSuccessfully() {
        List<StreamType> expected = Lists.list(EXPECTED_STREAM_TYPE);

        when(streamTypeRepositoryMock.findAll()).thenReturn(expected);

        Collection<StreamType> actual = streamTypeService.getStreamTypes();

        assertEquals(expected, actual, "Wrong stream types found.");
    }
}