package com.scalefocus.java.simeonyachev.domain.mysql;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.scalefocus.java.simeonyachev.util.DurationSerializer;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Multimedia {

    @Id
    private Integer id;
    private String title;
    private String description;
    private Float rating;
    private String directors;
    private String writers;
    @JsonSerialize(using = DurationSerializer.class)
    private Integer duration;
    private Integer year;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getWriters() {
        return writers;
    }

    public void setWriters(String writers) {
        this.writers = writers;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Multimedia)) {
            return false;
        }
        Multimedia that = (Multimedia) obj;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title)
                && Objects.equals(description, that.description) && Objects.equals(rating, that.rating)
                && Objects.equals(directors, that.directors) && Objects.equals(writers, that.writers)
                && Objects.equals(duration, that.duration) && Objects.equals(year, that.year)
                && Objects.equals(stars, that.stars)
                && Objects.equals(releaseDate, that.releaseDate) && Objects.equals(imdbId, that.imdbId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, rating, directors, writers, duration, year, stars, releaseDate, imdbId);
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
