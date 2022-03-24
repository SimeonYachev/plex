package com.scalefocus.java.simeonyachev.domain.mysql;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "series")
public class Series extends Multimedia {

    private String genres;

    @OneToMany(mappedBy = "series", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    private Collection<Season> seasons;

    public Series() {
        super();
    }

    private Series(SeriesBuilder builder) {
        super(builder);
        this.genres = builder.genres;
        this.seasons = builder.seasons;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public Collection<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(Collection<Season> seasons) {
        this.seasons = seasons;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Series)) {
            return false;
        }
        Series series = (Series) obj;
        return Objects.equals(genres, series.genres) && Objects.equals(seasons, series.seasons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genres, seasons);
    }

    public static class SeriesBuilder extends MultimediaBuilder<Series, SeriesBuilder> {

        private String genres;
        private Collection<Season> seasons;

        @Override
        protected SeriesBuilder getThis() {
            return this;
        }

        public SeriesBuilder() {
            super();
        }

        public Series build() {
            return new Series(this);
        }

        public SeriesBuilder genres(String genres) {
            this.genres = genres;
            return this;
        }

        public SeriesBuilder seasons(Collection<Season> seasons) {
            this.seasons = seasons;
            return this;
        }
    }
}
