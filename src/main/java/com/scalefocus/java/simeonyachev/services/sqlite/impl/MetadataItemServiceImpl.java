package com.scalefocus.java.simeonyachev.services.sqlite.impl;

import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;
import com.scalefocus.java.simeonyachev.exceptions.sqlite.MetadataItemNotFoundException;
import com.scalefocus.java.simeonyachev.repositories.sqlite.MetadataItemRepository;
import com.scalefocus.java.simeonyachev.services.sqlite.MetadataItemService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MetadataItemServiceImpl implements MetadataItemService {

    private final MetadataItemRepository metadataItemRepository;

    public MetadataItemServiceImpl(MetadataItemRepository metadataItemRepository) {
        this.metadataItemRepository = metadataItemRepository;
    }

    @Override
    public MetadataItem getById(Integer id) {
        return metadataItemRepository.findById(id)
                .orElseThrow(() -> new MetadataItemNotFoundException(id, "id"));
    }

    @Override
    public MetadataItem getByTitle(String title) {
        return metadataItemRepository.findByTitle(title)
                .orElseThrow(() -> new MetadataItemNotFoundException(title));
    }

    @Override
    public Collection<MetadataItem> getAllByMetadataType(Integer type) {
        return metadataItemRepository.findAllByMetadataType(type)
                .orElseThrow(() -> new MetadataItemNotFoundException(type, "type"));
    }

    @Override
    public Collection<MetadataItem> getAllSeasonsOfSeries(Integer seriesId) {
        return metadataItemRepository.findAllSeasonsOfSeries(seriesId)
                .orElseThrow(() -> new MetadataItemNotFoundException("seasons", "series", seriesId));
    }

    @Override
    public Collection<MetadataItem> getAllEpisodesOfSeason(Integer seasonId) {
        return metadataItemRepository.findAllEpisodesOfSeason(seasonId)
                .orElseThrow(() -> new MetadataItemNotFoundException("episodes", "season", seasonId));
    }
}
