package com.scalefocus.java.simeonyachev.domain.mysql;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.scalefocus.java.simeonyachev.util.DurationSerializer;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Multimedia {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "directors")
    private String directors;

    @Column(name = "writers")
    private String writers;

    @Column(name = "duration")
    @JsonSerialize(using = DurationSerializer.class)
    private Integer duration;

    @Column(name = "year")
    private Integer year;

    @Column(name = "stars")
    private String stars;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "imdb_id")
    private String imdbId;

    protected Multimedia() {
    }

    protected Multimedia(MultimediaBuilder<?, ?> builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.rating = builder.rating;
        this.directors = builder.directors;
        this.writers = builder.writers;
        this.duration = builder.duration;
        this.year = builder.year;
        this.stars = builder.stars;
        this.releaseDate = builder.releaseDate;
        this.imdbId = builder.imdbId;
    }

    public abstract static class MultimediaBuilder<T extends Multimedia, B extends MultimediaBuilder<T, B>> {

        private Integer id;
        private String title;
        private String description;
        private Float rating;
        private String directors;
        private String writers;
        private Integer duration;
        private Integer year;
        private String stars;
        private String releaseDate;
        private String imdbId;

        protected abstract T build();

        protected abstract B getThis();

        public B id(Integer id) {
            this.id = id;
            return getThis();
        }

        public B title(String title) {
            this.title = title;
            return getThis();
        }

        public B description(String description) {
            this.description = description;
            return getThis();
        }

        public B rating(Float rating) {
            this.rating = rating;
            return getThis();
        }

        public B directors(String directors) {
            this.directors = directors;
            return getThis();
        }

        public B writers(String writers) {
            this.writers = writers;
            return getThis();
        }

        public B duration(Integer duration) {
            this.duration = duration;
            return getThis();
        }

        public B year(Integer year) {
            this.year = year;
            return getThis();
        }

        public B stars(String stars) {
            this.stars = stars;
            return getThis();
        }

        public B releaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
            return getThis();
        }

        public B imdbId(String imdbId) {
            this.imdbId = imdbId;
            return getThis();
        }
    }
}