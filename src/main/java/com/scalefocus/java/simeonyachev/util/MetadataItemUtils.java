package com.scalefocus.java.simeonyachev.util;

import com.scalefocus.java.simeonyachev.domain.sqlite.MediaItem;
import com.scalefocus.java.simeonyachev.domain.sqlite.MediaStream;
import com.scalefocus.java.simeonyachev.domain.sqlite.MetadataItem;
import com.scalefocus.java.simeonyachev.domain.sqlite.StreamType;
import com.scalefocus.java.simeonyachev.services.sqlite.MediaItemService;
import com.scalefocus.java.simeonyachev.services.sqlite.MediaStreamService;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class MetadataItemUtils {

    private static final int START_INDEX = 0;
    private static final int YEAR_LENGTH = 4;
    private static final String IMDB = "imdb";
    private static final String TT = "tt";
    private static final String QUEST_MARK = "?";

    private final MediaItemService mediaItemService;
    private final MediaStreamService mediaStreamService;

    public MetadataItemUtils(MediaItemService mediaItemService, MediaStreamService mediaStreamService) {
        this.mediaItemService = mediaItemService;
        this.mediaStreamService = mediaStreamService;
    }

    public Integer extractDuration(MetadataItem metadataItem) {
        Collection<MediaItem> mediaItems = mediaItemService.getAllByMetadataItem(metadataItem);
        Integer duration = null;
        for (MediaItem mediaItem : mediaItems) {
            duration = mediaItem.getDuration();
            if (duration != null) {
                return duration;
            }
        }

        return duration;
    }

    public String extractImdbId(MetadataItem metadataItem) {
        String guid = metadataItem.getGuid();
        if (guid == null || !guid.contains(IMDB)) {
            return null;
        }

        int ttIndex = guid.indexOf(TT);
        int questionMarkIndex = guid.substring(ttIndex).indexOf(QUEST_MARK) + guid.substring(START_INDEX, ttIndex).length();

        return guid.substring(ttIndex, questionMarkIndex);
    }

    public Integer extractYear(MetadataItem metadataItem) {
        if (metadataItem.getOriginallyAvailableAt() == null) {
            return null;
        }
        return Integer.parseInt(metadataItem.getOriginallyAvailableAt().substring(START_INDEX, YEAR_LENGTH));
    }

    public String extractLanguages(MetadataItem metadataItem, Integer id) {
        Collection<MediaItem> mediaItems = mediaItemService.getAllByMetadataItem(metadataItem);

        Set<String> languages = new HashSet<>();
        for (MediaItem mediaItem : mediaItems) {
            Collection<MediaStream> mediaStreams =  mediaStreamService.getAllByMediaItem(mediaItem);

            for (MediaStream mediaStream : mediaStreams) {
                String language = mediaStream.getLanguage();
                StreamType streamType = mediaStream.getStreamType();
                if (language != null && streamType != null && !language.isEmpty() && id.equals(streamType.getId())) {
                    languages.add(language);
                }
            }
        }
        return languages.toString();
    }

    public String extractDistinctInfo(String info) {
        return new HashSet<String>(Arrays.asList(info.split("\\|"))).toString();
    }
}
