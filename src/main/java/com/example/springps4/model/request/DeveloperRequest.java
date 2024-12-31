package com.example.springps4.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

public class DeveloperRequest {
    @Id
    private Long developer_id;

    @JsonProperty
    private String developer;

    @JsonProperty
    private Integer game_id;

    public Long getDeveloper_id() {
        return developer_id;
    }

    public void setDeveloper_id(Long developer_id) {
        this.developer_id = developer_id;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }
}
