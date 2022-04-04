package com.scalefocus.java.simeonyachev.controllers;

import com.scalefocus.java.simeonyachev.services.MigrationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/migrate")
public class MigrationController {

    private final MigrationService migrationService;

    public MigrationController(MigrationService migrationService) {
        this.migrationService = migrationService;
    }

    @PostMapping
    @ApiOperation(value = "Migrates all movies and series from the SQLite to the MySQL database")
    public ResponseEntity<String> migrateAll(@RequestParam("file")
                                                 @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(migrationService.migrateAll(file));
    }

    @PostMapping("/movies")
    @ApiOperation(value = "Migrates all movies from the SQLite to the MySQL database")
    public ResponseEntity<String> migrateMovies(@RequestParam("file")
                                                    @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(migrationService.migrateMovies(file));
    }

    @PostMapping("/series")
    @ApiOperation(value = "Migrates all series from the SQLite to the MySQL database")
    public ResponseEntity<String> migrateSeries(@RequestParam("file")
                                                    @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(migrationService.migrateSeries(file));
    }
}
