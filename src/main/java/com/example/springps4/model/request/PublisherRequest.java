package com.example.springps4.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

public class PublisherRequest {
    @Id
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

    @Override
    public String toString() {
        return "PublisherRequest{" +
                "publisher_id=" + publisher_id +
                ", publisher='" + publisher + '\'' +
                ", game_id=" + game_id +
                '}';
    }
}
