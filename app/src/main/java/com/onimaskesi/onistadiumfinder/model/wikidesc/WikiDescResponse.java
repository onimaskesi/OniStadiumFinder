package com.onimaskesi.onistadiumfinder.model.wikidesc;

import com.google.gson.annotations.SerializedName;

public class WikiDescResponse {

    @SerializedName("query")
    PageList query;

    public WikiDescResponse(PageList query) {
        this.query = query;
    }

    public PageList getQuery() {
        return query;
    }

    public void setQuery(PageList query) {
        this.query = query;
    }
}
