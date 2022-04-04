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
@Table(name = "metadata_items")
public class MetadataItem {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "metadata_type")
    private Integer metadataType;

    @Column(name = "guid")
    private String guid;

    @Column(name = "title")
    private String title;

    @Column(name = "studio")
    private String studio;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "summary")
    private String summary;

    @Column(name = "[index]")
    private Integer index;

    @Column(name = "content_rating")
    private String contentRating;

    @Column(name = "originally_available_at")
    private String originallyAvailableAt;

    @Column(name = "tags_genre")
    private String tagsGenre;

    @Column(name = "tags_director")
    private String tagsDirector;

    @Column(name = "tags_writer")
    private String tagsWriter;

    @Column(name = "tags_star")
    private String tagsStar;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private MetadataItem parentMetadataItem;
}