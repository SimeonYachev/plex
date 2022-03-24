package com.scalefocus.java.simeonyachev.services.mysql.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.java.simeonyachev.domain.mysql.Movie;
import com.scalefocus.java.simeonyachev.exceptions.mysql.MovieNotFoundException;
import com.scalefocus.java.simeonyachev.repositories.mysql.MovieRepository;
import com.scalefocus.java.simeonyachev.services.mysql.MovieService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@Service
public class MovieServiceImpl implements MovieService {

    private static final String OMDB_API_KEY = "12f5884f";
    private static final String GET_MOVIE_INFO_BY_TITLE_URL = "http://www.omdbapi.com/?apikey=" + OMDB_API_KEY + "&t=";

    private final MovieRepository movieRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public MovieServiceImpl(MovieRepository movieRepository, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.movieRepository = movieRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Movie getById(Integer id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));

        if (movie.getImdbId() == null) {
            String finalUrl = GET_MOVIE_INFO_BY_TITLE_URL + movie.getTitle().replace(" ", "+");
            String responseBody = restTemplate.getForEntity(finalUrl, String.class).getBody();

            String imdbId;
            try {
                imdbId = objectMapper.readTree(responseBody).path("imdbID").asText();
            } catch (JsonProcessingException exception) {
                imdbId = null;
            }
            movie.setImdbId(imdbId);
        }

        return movie;
    }

    @Override
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Collection<Movie> saveMovies(Collection<Movie> movies) {
        return movieRepository.saveAll(movies);
    }
}
