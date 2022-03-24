package com.scalefocus.java.simeonyachev.domain.sqlite;

import javax.persistence.*;
import java.util.Objects;

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

    public MetadataItem() {
    }

    public MetadataItem(Integer id, Integer metadataType, String guid, String title, String studio,
                        Float rating, String summary, Integer index, String contentRating,
                        String originallyAvailableAt, String tagsGenre, String tagsDirector,
                        String tagsWriter, String tagsStar, MetadataItem parentMetadataItem) {
        this.id = id;
        this.index = index;
        this.parentMetadataItem = parentMetadataItem;
        this.metadataType = metadataType;
        this.guid = guid;
        this.title = title;
        this.studio = studio;
        this.rating = rating;
        this.summary = summary;
        this.contentRating = contentRating;
        this.originallyAvailableAt = originallyAvailableAt;
        this.tagsGenre = tagsGenre;
        this.tagsDirector = tagsDirector;
        this.tagsWriter = tagsWriter;
        this.tagsStar = tagsStar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMetadataType() {
        return metadataType;
    }

    public void setMetadataType(Integer metadataType) {
        this.metadataType = metadataType;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getOriginallyAvailableAt() {
        return originallyAvailableAt;
    }

    public void setOriginallyAvailableAt(String originallyAvailableAt) {
        this.originallyAvailableAt = originallyAvailableAt;
    }

    public String getTagsGenre() {
        return tagsGenre;
    }

    public void setTagsGenre(String tagsGenre) {
        this.tagsGenre = tagsGenre;
    }

    public String getTagsDirector() {
        return tagsDirector;
    }

    public void setTagsDirector(String tagsDirector) {
        this.tagsDirector = tagsDirector;
    }

    public String getTagsWriter() {
        return tagsWriter;
    }

    public void setTagsWriter(String tagsWriter) {
        this.tagsWriter = tagsWriter;
    }

    public String getTagsStar() {
        return tagsStar;
    }

    public void setTagsStar(String tagsStar) {
        this.tagsStar = tagsStar;
    }

    public MetadataItem getParentMetadataItem() {
        return parentMetadataItem;
    }

    public void setParentMetadataItem(MetadataItem parentMetadataItem) {
        this.parentMetadataItem = parentMetadataItem;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MetadataItem)) {
            return false;
        }
        MetadataItem that = (MetadataItem) obj;
        return id.equals(that.id) && Objects.equals(metadataType, that.metadataType)
                && Objects.equals(guid, that.guid) && Objects.equals(title, that.title)
                && Objects.equals(studio, that.studio) && Objects.equals(rating, that.rating)
                && Objects.equals(summary, that.summary) && Objects.equals(contentRating, that.contentRating)
                && Objects.equals(originallyAvailableAt, that.originallyAvailableAt) && Objects.equals(index, that.index)
                && Objects.equals(tagsGenre, that.tagsGenre) && Objects.equals(tagsDirector, that.tagsDirector)
                && Objects.equals(tagsWriter, that.tagsWriter) && Objects.equals(tagsStar, that.tagsStar)
                && Objects.equals(parentMetadataItem, that.parentMetadataItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, metadataType, guid, title, index,
                studio, rating, summary, contentRating, originallyAvailableAt,
                tagsGenre, tagsDirector, tagsWriter, tagsStar, parentMetadataItem);
    }
}
