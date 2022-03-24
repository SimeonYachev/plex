package com.scalefocus.java.simeonyachev.services;

import org.springframework.web.multipart.MultipartFile;

public interface MigrationService {

    String migrateAll(MultipartFile file);

    String migrateMovies(MultipartFile file);

    String migrateSeries(MultipartFile file);
}
