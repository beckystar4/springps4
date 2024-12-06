package com.example.springps4.model.response.base;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchResponse {
    @JsonProperty
    protected int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
