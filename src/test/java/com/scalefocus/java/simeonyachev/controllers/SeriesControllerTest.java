package com.scalefocus.java.simeonyachev.controllers;

import com.scalefocus.java.simeonyachev.domain.mysql.Series;
import com.scalefocus.java.simeonyachev.exceptions.mysql.SeriesNotFoundException;
import com.scalefocus.java.simeonyachev.security.jwt.JwtUtil;
import com.scalefocus.java.simeonyachev.services.mysql.SeriesService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(SeriesController.class)
class SeriesControllerTest {

    private static final String SERIES_ID_ENDPOINT = "/api/series/1";
    private static Series EXPECTED_SERIES;

    @MockBean
    private UserDetailsService userDetailsServiceMock;
    @MockBean
    private JwtUtil jwtUtilMock;
    @MockBean
    private SeriesService seriesServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void setExpectedSeries() {
        EXPECTED_SERIES = new Series.SeriesBuilder()
                .title("expectedTitle")
                .build();
    }

    @Test
    @WithMockUser(username = "username")
    void getSeriesByIdSuccessfully() throws Exception {
        when(seriesServiceMock.getById(anyInt())).thenReturn(EXPECTED_SERIES);

        mockMvc.perform(get(SERIES_ID_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(1)))
                .andExpect(jsonPath("$.title").value("expectedTitle"));

        verify(seriesServiceMock).getById(anyInt());
    }

    @Test
    @WithMockUser(username = "username")
    void getSeriesByIdNotFound() throws Exception {
        when(seriesServiceMock.getById(anyInt())).thenThrow(new SeriesNotFoundException(1));

        mockMvc.perform(get(SERIES_ID_ENDPOINT))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cannot find series with id " + 1));

        verify(seriesServiceMock).getById(anyInt());
    }
}