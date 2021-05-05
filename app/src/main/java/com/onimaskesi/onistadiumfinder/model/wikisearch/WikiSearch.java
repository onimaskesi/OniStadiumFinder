package com.onimaskesi.onistadiumfinder.model.wikisearch;

import com.google.gson.annotations.SerializedName;

public class WikiSearch {

    @SerializedName("title")
    private String title;

    @SerializedName("pageid")
    private int pageid;

    public WikiSearch(String title, int pageid) {
        this.title = title;
        this.pageid = pageid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPageid() {
        return pageid;
    }

    public void setPageid(int pageid) {
        this.pageid = pageid;
    }
}
