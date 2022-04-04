package com.scalefocus.java.simeonyachev.services.impl;

import com.scalefocus.java.simeonyachev.domain.mysql.Movie;
import com.scalefocus.java.simeonyachev.domain.mysql.Series;
import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;
import com.scalefocus.java.simeonyachev.exceptions.FileUploadFailureException;
import com.scalefocus.java.simeonyachev.services.MigrationService;
import com.scalefocus.java.simeonyachev.services.mysql.MovieService;
import com.scalefocus.java.simeonyachev.services.mysql.SeriesService;
import com.scalefocus.java.simeonyachev.services.sqlite.MetadataItemService;
import com.scalefocus.java.simeonyachev.util.mappers.MovieMapper;
import com.scalefocus.java.simeonyachev.util.mappers.SeriesMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class MigrationServiceImpl implements MigrationService {

    private static final int MOVIE_TYPE = 1;
    private static final int SERIES_TYPE = 2;
    private static final String FILE_PATH =
            "C:\\Users\\simeon.yachev\\IdeaProjects\\db_files\\uploaded_db.db";

    private final MovieMapper movieMapper;
    private final MovieService movieService;
    private final MetadataItemService metadataItemService;
    private final SeriesMapper seriesMapper;
    private final SeriesService seriesService;

    public MigrationServiceImpl(MovieMapper movieMapper, MovieService movieService,
                                SeriesMapper seriesMapper, SeriesService seriesService,
                                MetadataItemService metadataItemService1) {
        this.movieMapper = movieMapper;
        this.movieService = movieService;
        this.seriesMapper = seriesMapper;
        this.seriesService = seriesService;
        this.metadataItemService = metadataItemService1;
    }

    @Override
    public String migrateAll(MultipartFile file) {
        uploadFile(file);

        Collection<MetadataItem> metadataMovies = metadataItemService.getAllByMetadataType(MOVIE_TYPE);
        Collection<Movie> movies = mapMovies(metadataMovies);
        movieService.saveMovies(movies);

        Collection<MetadataItem> metadataSeries = metadataItemService.getAllByMetadataType(SERIES_TYPE);
        Collection<Series> series = mapSeries(metadataSeries);
        seriesService.saveSeries(series);

        return movies.size() + " movies and " + series.size() + " series migrated successfully!";
    }

    @Override
    public String migrateMovies(MultipartFile file) {
        uploadFile(file);

        Collection<MetadataItem> metadataMovies = metadataItemService.getAllByMetadataType(MOVIE_TYPE);

        Collection<Movie> movies = mapMovies(metadataMovies);
        movieService.saveMovies(movies);
        return movies.size() + " movies migrated successfully!";
    }

    @Override
    public String migrateSeries(MultipartFile file) {
        uploadFile(file);

        Collection<MetadataItem> metadataSeries = metadataItemService.getAllByMetadataType(SERIES_TYPE);

        Collection<Series> series = mapSeries(metadataSeries);
        seriesService.saveSeries(series);

        return series.size() + " series migrated successfully!";
    }

    private Collection<Movie> mapMovies(Collection<MetadataItem> metadataMovies) {
        return metadataMovies.stream()
                .map(movieMapper::toMovie)
                .collect(Collectors.toList());
    }

    private Collection<Series> mapSeries(Collection<MetadataItem> metadataSeries) {
        return metadataSeries.stream()
                .map(seriesMapper::toSeries)
                .collect(Collectors.toList());
    }

    private void uploadFile(MultipartFile file) {
        File myFile = new File(FILE_PATH);
        try (FileOutputStream fos = new FileOutputStream(myFile)) {
            myFile.createNewFile();
            fos.write(file.getBytes());
        } catch (IOException exception) {
            throw new FileUploadFailureException();
        }
    }
}
