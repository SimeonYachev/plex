package com.scalefocus.java.simeonyachev.domain.mysql;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "episodes")
public class Episode extends Multimedia {

    @Column(name = "number")
    private Integer number;

    @Column(name = "audio")
    private String audio;

    @Column(name = "subtitles")
    private String subtitles;

    @ManyToOne
    @JoinColumn(name = "season_id")
    @JsonBackReference
    private Season season;

    public Episode() {
        super();
    }

    private Episode(EpisodeBuilder builder) {
        super(builder);
        this.number = builder.number;
        this.audio = builder.audio;
        this.subtitles = builder.subtitles;
        this.season = builder.season;
    }

    public static class EpisodeBuilder extends MultimediaBuilder<Episode, EpisodeBuilder> {
        private Integer number;
        private String audio;
        private String subtitles;
        private Season season;

        @Override
        protected EpisodeBuilder getThis() {
            return this;
        }

        public EpisodeBuilder() {
            super();
        }

        public Episode build() {
            return new Episode(this);
        }

        public EpisodeBuilder number(Integer number) {
            this.number = number;
            return this;
        }

        public EpisodeBuilder audio(String audio) {
            this.audio = audio;
            return this;
        }

        public EpisodeBuilder subtitles(String subtitles) {
            this.subtitles = subtitles;
            return this;
        }

        public EpisodeBuilder season(Season season) {
            this.season = season;
            return this;
        }
    }
}