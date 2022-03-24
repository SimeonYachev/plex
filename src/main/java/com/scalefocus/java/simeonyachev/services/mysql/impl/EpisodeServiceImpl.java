package com.scalefocus.java.simeonyachev.services.mysql.impl;

import com.scalefocus.java.simeonyachev.domain.mysql.Episode;
import com.scalefocus.java.simeonyachev.exceptions.mysql.EpisodeNotFoundException;
import com.scalefocus.java.simeonyachev.repositories.mysql.EpisodeRepository;
import com.scalefocus.java.simeonyachev.services.mysql.EpisodeService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EpisodeServiceImpl implements EpisodeService {

    private final EpisodeRepository episodeRepository;

    public EpisodeServiceImpl(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    @Override
    public Episode getById(Integer id) {
        return episodeRepository.findById(id)
                .orElseThrow(() -> new EpisodeNotFoundException(id));
    }

    @Override
    public Episode saveEpisode(Episode ep) {
        return episodeRepository.save(ep);
    }

    @Override
    public Collection<Episode> saveEpisodes(Collection<Episode> episodes) {
        return episodeRepository.saveAll(episodes);
    }
}
