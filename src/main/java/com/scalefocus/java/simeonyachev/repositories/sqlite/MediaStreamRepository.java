package com.scalefocus.java.simeonyachev.repositories.sqlite;

import com.scalefocus.java.simeonyachev.domain.sqlite.MediaItem;
import com.scalefocus.java.simeonyachev.domain.sqlite.MediaStream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface MediaStreamRepository extends JpaRepository<MediaStream, Integer> {
    Optional<MediaStream> findByLanguage(String language);
    Optional<Collection<MediaStream>> findAllByMediaItem(MediaItem mediaItem);
}
