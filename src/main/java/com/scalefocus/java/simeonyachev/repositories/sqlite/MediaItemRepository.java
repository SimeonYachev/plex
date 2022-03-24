package com.scalefocus.java.simeonyachev.repositories.sqlite;

import com.scalefocus.java.simeonyachev.domain.sqlite.MediaItem;
import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface MediaItemRepository extends JpaRepository<MediaItem, Integer> {
    Optional<Collection<MediaItem>> findAllByMetadataItem(MetadataItem metadataItem);
}
