package com.scalefocus.java.simeonyachev.services.mysql;

import com.scalefocus.java.simeonyachev.domain.mysql.Season;

import java.util.Collection;

public interface SeasonService {

    Season getById(Integer id);

    Season saveSeason(Season season);

    Collection<Season> saveSeasons(Collection<Season> seasons);
}
