package com.scalefocus.java.simeonyachev.domain.sqlite;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "media_items")
public class MediaItem {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "size")
    private Integer size;

    @Column(name = "duration")
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "metadata_item_id")
    private MetadataItem metadataItem;

    public MediaItem() {
    }

    public MediaItem(Integer id, MetadataItem metadataItem, Integer width,
                     Integer height, Integer size, Integer duration) {
        this.id = id;
        this.metadataItem = metadataItem;
        this.width = width;
        this.height = height;
        this.size = size;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MetadataItem getMetadataItem() {
        return metadataItem;
    }

    public void setMetadataItem(MetadataItem metadataItem) {
        this.metadataItem = metadataItem;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaItem)) {
            return false;
        }
        MediaItem mediaItem = (MediaItem) obj;
        return id.equals(mediaItem.id) && Objects.equals(metadataItem, mediaItem.metadataItem)
                && Objects.equals(width, mediaItem.width)
                && Objects.equals(height, mediaItem.height) && Objects.equals(size, mediaItem.size)
                && Objects.equals(duration, mediaItem.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, metadataItem, width, height, size, duration);
    }
}
