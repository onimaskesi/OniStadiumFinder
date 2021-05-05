package com.onimaskesi.onistadiumfinder.model.wikisearch;

import com.google.gson.annotations.SerializedName;

public class QueryWS {
    @SerializedName("query")
    private WikiSearchList wikiSearchList;

    public QueryWS(WikiSearchList wikiSearchList) {
        this.wikiSearchList = wikiSearchList;
    }

    public WikiSearchList getWikiSearchList() {
        return wikiSearchList;
    }

    public void setWikiSearchList(WikiSearchList wikiSearchList) {
        this.wikiSearchList = wikiSearchList;
    }
}
