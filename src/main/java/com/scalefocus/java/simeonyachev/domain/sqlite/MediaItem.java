package com.scalefocus.java.simeonyachev.domain.sqlite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}