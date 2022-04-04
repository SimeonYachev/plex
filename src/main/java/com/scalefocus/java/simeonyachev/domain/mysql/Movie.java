package com.scalefocus.java.simeonyachev.domain.mysql;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "movies")
public class Movie extends Multimedia {

    @Column(name = "genres")
    private String genres;

    @Column(name = "audio")
    private String audio;

    @Column(name = "subtitles")
    private String subtitles;

    public Movie() {
        super();
    }

    private Movie(MovieBuilder builder) {
        super(builder);
        this.genres = builder.genres;
        this.audio = builder.audio;
        this.subtitles = builder.subtitles;
    }

    public static class MovieBuilder extends MultimediaBuilder<Movie, MovieBuilder> {
        private String genres;
        private String audio;
        private String subtitles;

        @Override
        protected MovieBuilder getThis() {
            return this;
        }

        public MovieBuilder() {
            super();
        }

        public Movie build() {
            return new Movie(this);
        }

        public MovieBuilder genres(String genres) {
            this.genres = genres;
            return this;
        }

        public MovieBuilder audio(String audio) {
            this.audio = audio;
            return this;
        }

        public MovieBuilder subtitles(String subtitles) {
            this.subtitles = subtitles;
            return this;
        }
    }
}