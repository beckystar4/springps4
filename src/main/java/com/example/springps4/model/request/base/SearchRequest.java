package com.example.springps4.model.request.base;

import com.example.springps4.constants.SearchConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchRequest {
    @JsonProperty
    protected int size = SearchConstants.DEFAULT_SIZE;

    @JsonProperty
    protected  int index = SearchConstants.DEFAULT_INDEX;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
