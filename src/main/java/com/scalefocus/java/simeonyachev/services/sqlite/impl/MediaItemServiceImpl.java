package com.scalefocus.java.simeonyachev.services.sqlite.impl;

import com.scalefocus.java.simeonyachev.domain.sqlite.MediaItem;
import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;
import com.scalefocus.java.simeonyachev.exceptions.sqlite.MediaItemNotFoundException;
import com.scalefocus.java.simeonyachev.repositories.sqlite.MediaItemRepository;
import com.scalefocus.java.simeonyachev.services.sqlite.MediaItemService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MediaItemServiceImpl implements MediaItemService {

    private final MediaItemRepository mediaItemRepository;

    public MediaItemServiceImpl(MediaItemRepository mediaItemRepository) {
        this.mediaItemRepository = mediaItemRepository;
    }

    @Override
    public MediaItem getById(Integer id) {
        return mediaItemRepository.findById(id)
                .orElseThrow(() -> new MediaItemNotFoundException(id));
    }

    @Override
    public Collection<MediaItem> getAllByMetadataItem(MetadataItem metadataItem) {
        return mediaItemRepository.findAllByMetadataItem(metadataItem)
                .orElseThrow(() -> new MediaItemNotFoundException(metadataItem));
    }

    @Override
    public Collection<MediaItem> getAllMediaItems() {
        return mediaItemRepository.findAll();
    }
}
