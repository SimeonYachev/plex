package com.scalefocus.java.simeonyachev.services.mysql;

import com.scalefocus.java.simeonyachev.domain.mysql.Movie;

import java.util.Collection;

public interface MovieService {

    Movie getById(Integer id);

    Movie saveMovie(Movie movie);

    Collection<Movie> saveMovies(Collection<Movie> movies);
}
