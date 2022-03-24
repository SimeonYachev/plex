package com.scalefocus.java.simeonyachev.controllers;

import com.scalefocus.java.simeonyachev.domain.mysql.Movie;
import com.scalefocus.java.simeonyachev.services.mysql.MovieService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns information about a Movie by its id",
            notes = "Provide an id to see detailed information about a certain Movie")
    public ResponseEntity<Movie> getMovieById(@ApiParam(value = "id of the movie whose info you need to retrieve", required = true)
                                                  @PathVariable Integer id) {
        return ResponseEntity.ok(movieService.getById(id));
    }
}
