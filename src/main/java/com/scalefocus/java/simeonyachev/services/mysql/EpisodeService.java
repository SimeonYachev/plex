package com.scalefocus.java.simeonyachev.services.mysql;

import com.scalefocus.java.simeonyachev.domain.mysql.Episode;

import java.util.Collection;

public interface EpisodeService {

    Episode getById(Integer id);

    Episode saveEpisode(Episode ep);

    Collection<Episode> saveEpisodes(Collection<Episode> episodes);
}
