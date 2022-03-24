package com.scalefocus.java.simeonyachev.domain.sqlite;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "media_streams")
public class MediaStream {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "codec")
    private String codec;

    @Column(name = "language")
    private String language;

    @ManyToOne
    @JoinColumn(name = "media_item_id")
    private MediaItem mediaItem;

    @ManyToOne
    @JoinColumn(name = "stream_type_id")
    private StreamType streamType;

    public MediaStream() {
    }

    public MediaStream(Integer id, MediaItem mediaItem, StreamType streamType, String codec, String language) {
        this.id = id;
        this.mediaItem = mediaItem;
        this.streamType = streamType;
        this.codec = codec;
        this.language = language;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MediaItem getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(MediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    public StreamType getStreamType() {
        return streamType;
    }

    public void setStreamType(StreamType streamType) {
        this.streamType = streamType;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaStream)) {
            return false;
        }
        MediaStream that = (MediaStream) obj;
        return id.equals(that.id) && Objects.equals(mediaItem, that.mediaItem)
                && Objects.equals(streamType, that.streamType) && Objects.equals(codec, that.codec)
                && Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mediaItem, streamType, codec, language);
    }
}
