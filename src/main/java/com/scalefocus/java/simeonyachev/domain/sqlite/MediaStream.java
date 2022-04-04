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
}