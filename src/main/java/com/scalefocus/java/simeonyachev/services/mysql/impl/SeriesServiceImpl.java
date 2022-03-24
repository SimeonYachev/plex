package com.scalefocus.java.simeonyachev.services.mysql.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.java.simeonyachev.domain.mysql.Episode;
import com.scalefocus.java.simeonyachev.domain.mysql.Season;
import com.scalefocus.java.simeonyachev.domain.mysql.Series;
import com.scalefocus.java.simeonyachev.exceptions.mysql.SeriesNotFoundException;
import com.scalefocus.java.simeonyachev.repositories.mysql.SeriesRepository;
import com.scalefocus.java.simeonyachev.services.mysql.SeriesService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@Service
public class SeriesServiceImpl implements SeriesService {

    private static final String OMDB_API_KEY = "12f5884f";
    private static final String GET_SERIES_INFO_BY_TITLE_URL = "http://www.omdbapi.com/?apikey=" + OMDB_API_KEY + "&t=";

    private final SeriesRepository seriesRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SeriesServiceImpl(SeriesRepository seriesRepository, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.seriesRepository = seriesRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Series getById(Integer id) {
        Series series = seriesRepository.findById(id)
                .orElseThrow(() -> new SeriesNotFoundException(id));

        String seriesInfoUrl = GET_SERIES_INFO_BY_TITLE_URL + series.getTitle().replace(" ", "+");
        if (series.getImdbId() == null) {
            String responseBody = restTemplate.getForEntity(seriesInfoUrl, String.class).getBody();

            String imdbId;
            try {
                imdbId = objectMapper.readTree(responseBody).path("imdbID").asText();
            } catch (JsonProcessingException exception) {
                imdbId = null;
            }
            series.setImdbId(imdbId);
        }

        Collection<Season> seasons = series.getSeasons();
        for (Season season : seasons) {
            Collection<Episode> episodes = season.getEpisodes();
            for (Episode episode : episodes) {
                if (episode.getImdbId() == null) {
                    String episodeInfoUrl = seriesInfoUrl + "&season=" + season.getNumber() + "&episode=" + episode.getNumber();
                    String responseBody = restTemplate.getForEntity(episodeInfoUrl, String.class).getBody();

                    String imdbId;
                    try {
                        imdbId = objectMapper.readTree(responseBody).path("imdbID").asText();
                    } catch (JsonProcessingException exception) {
                        imdbId = null;
                    }
                    episode.setImdbId(imdbId);
                }
            }
        }

        return series;
    }

    @Override
    public Series saveSeries(Series series) {
        return seriesRepository.save(series);
    }

    @Override
    public Collection<Series> saveSeries(Collection<Series> series) {
        return seriesRepository.saveAll(series);
    }
}
