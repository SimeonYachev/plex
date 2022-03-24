package com.scalefocus.java.simeonyachev.repositories.mysql;

import com.scalefocus.java.simeonyachev.domain.mysql.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Integer> {
}
