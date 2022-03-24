package com.scalefocus.java.simeonyachev.services.sqlite.impl;

import com.scalefocus.java.simeonyachev.domain.sqlite.StreamType;
import com.scalefocus.java.simeonyachev.exceptions.sqlite.StreamTypeNotFoundException;
import com.scalefocus.java.simeonyachev.repositories.sqlite.StreamTypeRepository;
import com.scalefocus.java.simeonyachev.services.sqlite.StreamTypeService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StreamTypeServiceImpl implements StreamTypeService {

    private final StreamTypeRepository streamTypeRepository;

    public StreamTypeServiceImpl(StreamTypeRepository streamTypeRepository) {
        this.streamTypeRepository = streamTypeRepository;
    }

    @Override
    public StreamType getById(Integer id) {
        return streamTypeRepository.findById(id)
                .orElseThrow(() -> new StreamTypeNotFoundException(id));
    }

    @Override
    public Collection<StreamType> getStreamTypes() {
        return streamTypeRepository.findAll();
    }
}
