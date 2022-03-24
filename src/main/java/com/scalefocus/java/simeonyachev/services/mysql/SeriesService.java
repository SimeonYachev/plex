package com.scalefocus.java.simeonyachev.services.mysql;

import com.scalefocus.java.simeonyachev.domain.mysql.Series;

import java.util.Collection;

public interface SeriesService {

    Series getById(Integer id);

    Series saveSeries(Series series);

    Collection<Series> saveSeries(Collection<Series> series);
}
