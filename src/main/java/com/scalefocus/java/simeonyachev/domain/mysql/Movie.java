package com.scalefocus.java.simeonyachev.domain.mysql;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "movies")
public class Movie extends Multimedia {

    private String genres;
    private String audio;
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

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Movie)) {
            return false;
        }
        Movie movie = (Movie) obj;
        return Objects.equals(genres, movie.genres) && Objects.equals(audio, movie.audio)
                && Objects.equals(subtitles, movie.subtitles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genres, audio, subtitles);
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
