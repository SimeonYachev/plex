package com.scalefocus.java.simeonyachev.domain.mysql;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "episodes")
public class Episode extends Multimedia {

    private Integer number;
    private String audio;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(String subtitles) {
        this.subtitles = subtitles;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Episode)) {
            return false;
        }
        Episode episode = (Episode) obj;
        return Objects.equals(number, episode.number) && Objects.equals(audio, episode.audio)
                && Objects.equals(subtitles, episode.subtitles) && Objects.equals(season, episode.season);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, audio, subtitles, season);
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