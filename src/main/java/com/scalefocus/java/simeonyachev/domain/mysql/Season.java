package com.scalefocus.java.simeonyachev.domain.mysql;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "seasons")
public class Season {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "number")
    private Integer number;

    @OneToMany(mappedBy = "season", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonManagedReference
    private Collection<Episode> episodes;

    @ManyToOne
    @JoinColumn(name = "series_id")
    @JsonBackReference
    private Series series;

    private Season(SeasonBuilder builder) {
        this.id = builder.id;
        this.number = builder.number;
        this.episodes = builder.episodes;
        this.series = builder.series;
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