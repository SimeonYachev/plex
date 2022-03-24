package com.scalefocus.java.simeonyachev.services.mysql.impl;

import com.scalefocus.java.simeonyachev.domain.mysql.Season;
import com.scalefocus.java.simeonyachev.exceptions.mysql.SeasonNotFoundException;
import com.scalefocus.java.simeonyachev.repositories.mysql.SeasonRepository;
import com.scalefocus.java.simeonyachev.services.mysql.SeasonService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SeasonServiceImpl implements SeasonService {

    private final SeasonRepository seasonRepository;

    public SeasonServiceImpl(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    @Override
    public Season getById(Integer id) {
        return seasonRepository.findById(id)
                .orElseThrow(() -> new SeasonNotFoundException(id));
    }

    @Override
    public Season saveSeason(Season season) {
        return seasonRepository.save(season);
    }

    @Override
    public Collection<Season> saveSeasons(Collection<Season> seasons) {
        return seasonRepository.saveAll(seasons);
    }
}
