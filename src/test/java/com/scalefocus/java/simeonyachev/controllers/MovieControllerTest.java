package com.scalefocus.java.simeonyachev.controllers;

import com.scalefocus.java.simeonyachev.domain.mysql.Movie;
import com.scalefocus.java.simeonyachev.exceptions.mysql.MovieNotFoundException;
import com.scalefocus.java.simeonyachev.security.jwt.JwtUtil;
import com.scalefocus.java.simeonyachev.services.mysql.MovieService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    private static final String MOVIE_ID_ENDPOINT = "/api/movies/1";
    private static Movie EXPECTED_MOVIE;

    @MockBean
    private UserDetailsService userDetailsServiceMock;
    @MockBean
    private JwtUtil jwtUtilMock;
    @MockBean
    private MovieService movieServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void setExpectedMovie() {
        EXPECTED_MOVIE = new Movie.MovieBuilder()
                .title("expectedTitle")
                .build();
    }

    @Test
    @WithMockUser(username = "username")
    void getMovieByIdSuccessfully() throws Exception {
        when(movieServiceMock.getById(anyInt())).thenReturn(EXPECTED_MOVIE);

        mockMvc.perform(get(MOVIE_ID_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(1)))
                .andExpect(jsonPath("$.title").value("expectedTitle"));

        verify(movieServiceMock).getById(anyInt());
    }

    @Test
    @WithMockUser(username = "username")
    void getMovieByIdNotFound() throws Exception {
        when(movieServiceMock.getById(anyInt())).thenThrow(new MovieNotFoundException(1));

        mockMvc.perform(get(MOVIE_ID_ENDPOINT))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cannot find movie with id " + 1));

        verify(movieServiceMock).getById(anyInt());
    }
}