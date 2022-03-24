package com.scalefocus.java.simeonyachev.services.impl;

import com.scalefocus.java.simeonyachev.domain.mysql.Movie;
import com.scalefocus.java.simeonyachev.domain.mysql.Series;
import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;
import com.scalefocus.java.simeonyachev.exceptions.sqlite.MetadataItemNotFoundException;
import com.scalefocus.java.simeonyachev.services.MigrationService;
import com.scalefocus.java.simeonyachev.services.mysql.MovieService;
import com.scalefocus.java.simeonyachev.services.mysql.SeriesService;
import com.scalefocus.java.simeonyachev.services.sqlite.MetadataItemService;
import com.scalefocus.java.simeonyachev.util.mappers.MovieMapper;
import com.scalefocus.java.simeonyachev.util.mappers.SeriesMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.*;

class MigrationServiceImplTest {

    private static final int MOVIE_TYPE = 1;
    private static final int SERIES_TYPE = 2;
    private static MultipartFile MULTIPART_FILE;

    private MovieMapper movieMapperMock;
    private MovieService movieServiceMock;
    private SeriesMapper seriesMapperMock;
    private SeriesService seriesServiceMock;
    private MetadataItemService metadataItemServiceMock;
    private MigrationService migrationService;

    @BeforeAll
    static void setMultipartFile() {
        MULTIPART_FILE = new MockMultipartFile("fileName", (byte[]) null);
    }

    @BeforeEach
    void setUp() {
        movieMapperMock = mock(MovieMapper.class);
        movieServiceMock = mock(MovieService.class);
        seriesMapperMock = mock(SeriesMapper.class);
        seriesServiceMock = mock(SeriesService.class);
        metadataItemServiceMock = mock(MetadataItemService.class);
        migrationService = new MigrationServiceImpl(movieMapperMock, movieServiceMock,
                                                    seriesMapperMock, seriesServiceMock, metadataItemServiceMock);
    }

    @Test
    void migrateAllSuccessfully() {
        String expected = "2 movies and 2 series migrated successfully!";
        setUpSuccessfulMovieMigrationBehaviour();
        setUpSuccessfulSeriesMigrationBehaviour();

        String actual = migrationService.migrateAll(MULTIPART_FILE);

        assertEquals(expected, actual, "Migrated movies and series do not match the ones expected.");
    }

    @Test
    void migrateAllMetadataMoviesNotFound() {
        when(metadataItemServiceMock.getAllByMetadataType(MOVIE_TYPE)).thenThrow(MetadataItemNotFoundException.class);

        assertThrows(MetadataItemNotFoundException.class, () -> migrationService.migrateAll(MULTIPART_FILE));
        verify(metadataItemServiceMock).getAllByMetadataType(MOVIE_TYPE);
    }

    @Test
    void migrateAllMetadataSeriesNotFound() {
        when(metadataItemServiceMock.getAllByMetadataType(SERIES_TYPE)).thenThrow(MetadataItemNotFoundException.class);

        assertThrows(MetadataItemNotFoundException.class, () -> migrationService.migrateAll(MULTIPART_FILE));
        verify(metadataItemServiceMock).getAllByMetadataType(SERIES_TYPE);
    }

    @Test
    void migrateMoviesSuccessfully() {
        String expected = "2 movies migrated successfully!";

        setUpSuccessfulMovieMigrationBehaviour();

        String actual = migrationService.migrateMovies(MULTIPART_FILE);

        assertEquals(expected, actual, "Migrated movies do not match the ones expected.");
    }

    @Test
    void migrateMoviesMetadataMoviesNotFound() {
        when(metadataItemServiceMock.getAllByMetadataType(MOVIE_TYPE)).thenThrow(MetadataItemNotFoundException.class);

        assertThrows(MetadataItemNotFoundException.class, () -> migrationService.migrateMovies(MULTIPART_FILE));
        verify(metadataItemServiceMock).getAllByMetadataType(MOVIE_TYPE);
    }

    @Test
    void migrateSeriesSuccessfully() {
        String expected = "2 series migrated successfully!";

        setUpSuccessfulSeriesMigrationBehaviour();

        String actual = migrationService.migrateSeries(MULTIPART_FILE);

        assertEquals(expected, actual, "Migrated series do not match the ones expected.");
    }

    @Test
    void migrateSeriesMetadataSeriesNotFound() {
        when(metadataItemServiceMock.getAllByMetadataType(SERIES_TYPE)).thenThrow(MetadataItemNotFoundException.class);

        assertThrows(MetadataItemNotFoundException.class, () -> migrationService.migrateSeries(MULTIPART_FILE));
        verify(metadataItemServiceMock).getAllByMetadataType(SERIES_TYPE);
    }

    private void setUpSuccessfulMovieMigrationBehaviour() {
        Collection<MetadataItem> expectedMetadataMovies = Lists.list(
                new MetadataItem(),
                new MetadataItem()
        );

        when(metadataItemServiceMock.getAllByMetadataType(MOVIE_TYPE)).thenReturn(expectedMetadataMovies);

        Movie expectedMovie = new Movie();
        Collection<Movie> expectedMovies = Lists.list(expectedMovie, new Movie());

        MetadataItem anyMetadataMovie = any();
        when(movieMapperMock.toMovie(anyMetadataMovie)).thenReturn(expectedMovie);

        Collection<Movie> anyMovies = anyCollection();
        when(movieServiceMock.saveMovies(anyMovies)).thenReturn(expectedMovies);
    }

    private void setUpSuccessfulSeriesMigrationBehaviour() {
        Collection<MetadataItem> expectedMetadataSeries = Lists.list(
                new MetadataItem(),
                new MetadataItem()
        );

        when(metadataItemServiceMock.getAllByMetadataType(SERIES_TYPE)).thenReturn(expectedMetadataSeries);

        Series expectedSeries = new Series();
        Collection<Series> expectedSeriesCollection = Lists.list(expectedSeries, new Series());

        MetadataItem anyMetadataMovie = any();
        when(seriesMapperMock.toSeries(anyMetadataMovie)).thenReturn(expectedSeries);

        Collection<Series> anySeriesCollection = anyCollection();
        when(seriesServiceMock.saveSeries(anySeriesCollection)).thenReturn(expectedSeriesCollection);
    }
}