package com.scalefocus.java.simeonyachev.controllers;

import com.scalefocus.java.simeonyachev.domain.mysql.Movie;
import com.scalefocus.java.simeonyachev.domain.mysql.Series;
import com.scalefocus.java.simeonyachev.exceptions.sqlite.MetadataItemNotFoundException;
import com.scalefocus.java.simeonyachev.security.jwt.JwtUtil;
import com.scalefocus.java.simeonyachev.services.MigrationService;
import com.scalefocus.java.simeonyachev.services.mysql.MovieService;
import com.scalefocus.java.simeonyachev.services.mysql.SeriesService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MigrationController.class)
class MigrationControllerTest {

    private static final int MOVIE_TYPE = 1;
    private static final int SERIES_TYPE = 2;
    private static final String ALL_MIGRATION_ENDPOINT = "/api/migrate";
    private static final String MOVIE_MIGRATION_ENDPOINT = "/api/migrate/movies";
    private static final String SERIES_MIGRATION_ENDPOINT = "/api/migrate/series";
    private static MockMultipartFile MULTIPART_FILE;

    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private MigrationService migrationService;
    @MockBean
    private MovieService movieService;
    @MockBean
    private SeriesService seriesService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void setMultipartFile() {
        MULTIPART_FILE = new MockMultipartFile("File", (byte[]) null);
    }

    @Test
    @WithMockUser(username = "username")
    void migrateAllSuccessfully() throws Exception {
        int moviesCount = setUpSuccessfulMovieMigrationBehaviour();
        int seriesCount = setUpSuccessfulSeriesMigrationBehaviour();

        when(migrationService.migrateAll(any(MultipartFile.class)))
                .thenReturn(moviesCount + " movies and " + seriesCount + " series migrated successfully!");

        mockMvc.perform(multipart(ALL_MIGRATION_ENDPOINT).file(MULTIPART_FILE))
                .andExpect(status().isOk())
                .andExpect(content().string("2 movies and 2 series migrated successfully!"));
    }

    @Test
    @WithMockUser(username = "username")
    void migrateAllMetadataMoviesNotFound() throws Exception {
        when(migrationService.migrateAll(any(MultipartFile.class))).thenThrow(new MetadataItemNotFoundException(MOVIE_TYPE, "type"));

        mockMvc.perform(multipart(ALL_MIGRATION_ENDPOINT).file(MULTIPART_FILE))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cannot find metadata item with type " + MOVIE_TYPE));

        verify(migrationService).migrateAll(any(MultipartFile.class));
    }

    @Test
    @WithMockUser(username = "username")
    void migrateAllMetadataSeriesNotFound() throws Exception {
        when(migrationService.migrateAll(any(MultipartFile.class))).thenThrow(new MetadataItemNotFoundException(SERIES_TYPE, "type"));

        mockMvc.perform(multipart(ALL_MIGRATION_ENDPOINT).file(MULTIPART_FILE))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cannot find metadata item with type " + SERIES_TYPE));

        verify(migrationService).migrateAll(any(MultipartFile.class));
    }

    @Test
    @WithMockUser(username = "username")
    void migrateMoviesSuccessfully() throws Exception {
        int moviesCount = setUpSuccessfulMovieMigrationBehaviour();
        when(migrationService.migrateMovies(any(MultipartFile.class))).thenReturn(moviesCount + " movies migrated successfully!");

        mockMvc.perform(multipart(MOVIE_MIGRATION_ENDPOINT).file(MULTIPART_FILE))
                .andExpect(status().isOk())
                .andExpect(content().string("2 movies migrated successfully!"));
    }

    @Test
    @WithMockUser(username = "username")
    void migrateMoviesMetadataMoviesNotFound() throws Exception {
        when(migrationService.migrateMovies(any(MultipartFile.class))).thenThrow(new MetadataItemNotFoundException(MOVIE_TYPE, "type"));

        mockMvc.perform(multipart(MOVIE_MIGRATION_ENDPOINT).file(MULTIPART_FILE))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cannot find metadata item with type " + MOVIE_TYPE));

        verify(migrationService).migrateMovies(any(MultipartFile.class));
    }

    @Test
    @WithMockUser(username = "username")
    void migrateSeriesSuccessfully() throws Exception {
        int seriesCount = setUpSuccessfulSeriesMigrationBehaviour();
        when(migrationService.migrateSeries(any(MultipartFile.class))).thenReturn(seriesCount + " series migrated successfully!");

        mockMvc.perform(multipart(SERIES_MIGRATION_ENDPOINT).file(MULTIPART_FILE))
                .andExpect(status().isOk())
                .andExpect(content().string("2 series migrated successfully!"));
    }

    @Test
    @WithMockUser(username = "username")
    void migrateSeriesMetadataSeriesNotFound() throws Exception {
        when(migrationService.migrateSeries(any(MultipartFile.class))).thenThrow(new MetadataItemNotFoundException(SERIES_TYPE, "type"));

        mockMvc.perform(multipart(SERIES_MIGRATION_ENDPOINT).file(MULTIPART_FILE))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cannot find metadata item with type " + SERIES_TYPE));

        verify(migrationService).migrateSeries(any(MultipartFile.class));
    }

    private int setUpSuccessfulMovieMigrationBehaviour() {
        Collection<Movie> expectedMovies = Lists.list(new Movie(), new Movie());
        when(movieService.saveMovies(anyCollection())).thenReturn(expectedMovies);
        return expectedMovies.size();
    }

    private int setUpSuccessfulSeriesMigrationBehaviour() {
        Collection<Series> expectedSeries = Lists.list(new Series(), new Series());
        when(seriesService.saveSeries(anyCollection())).thenReturn(expectedSeries);
        return expectedSeries.size();
    }
}