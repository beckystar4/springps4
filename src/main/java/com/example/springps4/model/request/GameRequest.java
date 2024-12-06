package com.example.springps4.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class GameRequest {

    @JsonProperty
    private String title;

    @JsonProperty
    private String genres;

    @JsonProperty
    private Integer millions_of_copies_sold;

    @JsonProperty
    private LocalDate release_date;

    public GameRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public Integer getMillions_of_copies_sold() {
        return millions_of_copies_sold;
    }

    public void setMillions_of_copies_sold(Integer millions_of_copies_sold) {
        this.millions_of_copies_sold = millions_of_copies_sold;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDate release_date) {
        this.release_date = release_date;
    }
}
