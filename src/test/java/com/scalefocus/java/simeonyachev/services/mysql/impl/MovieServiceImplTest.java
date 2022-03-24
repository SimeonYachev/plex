package com.scalefocus.java.simeonyachev.services.mysql.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.java.simeonyachev.domain.mysql.Movie;
import com.scalefocus.java.simeonyachev.exceptions.mysql.MovieNotFoundException;
import com.scalefocus.java.simeonyachev.repositories.mysql.MovieRepository;
import com.scalefocus.java.simeonyachev.services.mysql.MovieService;
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
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.*;

class MovieServiceImplTest {

    private static final String OMDB_API_KEY = "12f5884f";
    private static final String GET_MOVIE_INFO_BY_TITLE_URL = "http://www.omdbapi.com/?apikey=" + OMDB_API_KEY + "&t=";
    private static final String EXPECTED_JSON_REPLY = "{\"imdbID\":\"expectedImdbId\"}";

    private static Movie EXPECTED_MOVIE;
    private static String FINAL_URL;

    private MovieRepository movieRepositoryMock;
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private MovieService movieService;

    @BeforeAll
    static void setExpectedMovie() {
        EXPECTED_MOVIE = new Movie.MovieBuilder()
                .title("expectedTitle")
                .imdbId("expectedImdbId")
                .build();
        FINAL_URL = GET_MOVIE_INFO_BY_TITLE_URL + EXPECTED_MOVIE.getTitle().replace(" ", "+");
    }

    @BeforeEach
    void setUp() {
        movieRepositoryMock = mock(MovieRepository.class);
        restTemplate = mock(RestTemplate.class);
        objectMapper = mock(ObjectMapper.class);
        movieService = new MovieServiceImpl(movieRepositoryMock, restTemplate, objectMapper);
    }

    @Test
    void getMovieByIdSuccessfully() {
        int anyInt = anyInt();
        when(movieRepositoryMock.findById(anyInt)).thenReturn(Optional.of(EXPECTED_MOVIE));
        when(restTemplate.getForEntity(FINAL_URL, String.class)).thenReturn(ResponseEntity.ok(EXPECTED_JSON_REPLY));

        Movie actualMovie = movieService.getById(anyInt);

        verify(movieRepositoryMock).findById(anyInt);

        assertNotNull(actualMovie);
        assertEquals(EXPECTED_MOVIE, actualMovie, "Wrong movie found.");
    }

    @Test
    void getMovieByIdNotFound() {
        int anyInt = anyInt();
        when(movieRepositoryMock.findById(anyInt)).thenThrow(MovieNotFoundException.class);

        assertThrows(MovieNotFoundException.class, () -> movieService.getById(anyInt));
        verify(movieRepositoryMock).findById(anyInt);
    }

    @Test
    void saveMovieSuccessfully() {
        Movie anyMovie = any();
        when(movieRepositoryMock.save(anyMovie)).thenReturn(EXPECTED_MOVIE);

        Movie actualMovie = movieService.saveMovie(anyMovie);

        verify(movieRepositoryMock).save(anyMovie);

        assertNotNull(actualMovie);
        assertEquals(EXPECTED_MOVIE, actualMovie, "Saved movie does not match the one expected.");
    }

    @Test
    void saveMoviesSuccessfully() {
        List<Movie> expectedMovies = Lists.list(EXPECTED_MOVIE);

        Collection<Movie> anyCollection = anyCollection();
        when(movieRepositoryMock.saveAll(anyCollection)).thenReturn(expectedMovies);

        Collection<Movie> actualMovies = movieService.saveMovies(anyCollection);

        verify(movieRepositoryMock).saveAll(anyCollection);

        assertEquals(expectedMovies, actualMovies, "Saved movies do not match the ones expected.");
    }
}