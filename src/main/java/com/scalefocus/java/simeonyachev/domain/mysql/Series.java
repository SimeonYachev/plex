package com.scalefocus.java.simeonyachev.domain.mysql;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "series")
public class Series extends Multimedia {

    @Column(name = "genres")
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