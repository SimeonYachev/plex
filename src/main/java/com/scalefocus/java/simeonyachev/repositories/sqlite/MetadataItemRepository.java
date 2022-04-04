package com.scalefocus.java.simeonyachev.repositories.sqlite;

import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface MetadataItemRepository extends JpaRepository<MetadataItem, Integer> {
    Optional<MetadataItem> findByTitle(String title);

    Optional<Collection<MetadataItem>> findAllByMetadataType(Integer type);

    @Query(value = "SELECT * FROM metadata_items m WHERE m.parent_id = :series_id", nativeQuery = true)
    Optional<Collection<MetadataItem>> findAllSeasonsOfSeries(@Param("series_id") Integer seriesId);

    @Query(value = "SELECT * FROM metadata_items m WHERE m.parent_id = :season_id", nativeQuery = true)
    Optional<Collection<MetadataItem>> findAllEpisodesOfSeason(@Param("season_id") Integer seasonId);
}
