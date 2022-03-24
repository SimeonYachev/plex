package com.scalefocus.java.simeonyachev.services.sqlite.impl;

import com.scalefocus.java.simeonyachev.domain.sqlite.MediaItem;
import com.scalefocus.java.simeonyachev.domain.sqlite.MediaStream;
import com.scalefocus.java.simeonyachev.exceptions.sqlite.MediaStreamNotFoundException;
import com.scalefocus.java.simeonyachev.repositories.sqlite.MediaStreamRepository;
import com.scalefocus.java.simeonyachev.services.sqlite.MediaStreamService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class MediaStreamServiceImpl implements MediaStreamService {

    private final MediaStreamRepository mediaStreamRepository;

    public MediaStreamServiceImpl(MediaStreamRepository mediaStreamRepository) {
        this.mediaStreamRepository = mediaStreamRepository;
    }

    @Override
    public MediaStream getById(Integer id) {
        return mediaStreamRepository.findById(id)
                .orElseThrow(() -> new MediaStreamNotFoundException(id));
    }

    @Override
    public Collection<MediaStream> getAllByMediaItem(MediaItem mediaItem) {
        return mediaStreamRepository.findAllByMediaItem(mediaItem)
                .orElseThrow(() -> new MediaStreamNotFoundException(mediaItem));
    }

    @Override
    public Collection<String> getAllLanguages() {
        return mediaStreamRepository.findAll()
                .stream()
                .map(MediaStream::getLanguage)
                .collect(Collectors.toList());
    }
}
