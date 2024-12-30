package com.example.springps4.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PublisherResponse {
    @JsonProperty
    private Long publisher_id;

    @JsonProperty
    private String publisher;

    @JsonProperty
    private Integer game_id;

    public Long getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(Long publisher_id) {
        this.publisher_id = publisher_id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

}
