package com.scalefocus.java.simeonyachev.controllers;

import com.scalefocus.java.simeonyachev.domain.mysql.Series;
import com.scalefocus.java.simeonyachev.services.mysql.SeriesService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/series")
public class SeriesController {

    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns information about Series by its id",
            notes = "Provide an id to see detailed information about certain Series")
    public ResponseEntity<Series> getSeriesById(@ApiParam(value = "id of the series whose info you need to retrieve", required = true)
                                                    @PathVariable Integer id) {
        return ResponseEntity.ok(seriesService.getById(id));
    }
}
