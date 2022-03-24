package com.scalefocus.java.simeonyachev.domain.mysql;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "seasons")
public class Season {

    @Id
    private Integer id;
    private Integer number;

    @OneToMany(mappedBy = "season", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    private Collection<Episode> episodes;

    @ManyToOne
    @JoinColumn(name = "series_id")
    @JsonBackReference
    private Series series;

    public Season() {
    }

    private Season(SeasonBuilder builder) {
        this.id = builder.id;
        this.number = builder.number;
        this.episodes = builder.episodes;
        this.series = builder.series;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Collection<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Collection<Episode> episodes) {
        this.episodes = episodes;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Season)) {
            return false;
        }
        Season season = (Season) obj;
        return Objects.equals(id, season.id) && Objects.equals(number, season.number) && Objects.equals(episodes, season.episodes)
                && Objects.equals(series, season.series);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, episodes, series);
    }

    public static class SeasonBuilder {
        private Integer id;
        private Integer number;
        private Collection<Episode> episodes;
        private Series series;

        public SeasonBuilder() {}

        public Season build() {
            return new Season(this);
        }

        public SeasonBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public SeasonBuilder number(Integer number) {
            this.number = number;
            return this;
        }

        public SeasonBuilder episodes(Collection<Episode> episodes) {
            this.episodes = episodes;
            return this;
        }

        public SeasonBuilder series(Series series) {
            this.series = series;
            return this;
        }
    }
}