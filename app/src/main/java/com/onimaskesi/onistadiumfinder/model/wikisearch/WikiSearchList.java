package com.onimaskesi.onistadiumfinder.model.wikisearch;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WikiSearchList {

    @SerializedName("search")
    private List<WikiSearch> searchList;

    public WikiSearchList(List<WikiSearch> searchList) {
        this.searchList = searchList;
    }

    public List<WikiSearch> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<WikiSearch> searchList) {
        this.searchList = searchList;
    }
}
