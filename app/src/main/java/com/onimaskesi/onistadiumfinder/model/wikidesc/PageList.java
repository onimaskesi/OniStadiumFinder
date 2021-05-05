package com.onimaskesi.onistadiumfinder.model.wikidesc;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class PageList {
    @SerializedName("pages")
    Map<String, Page> pageList;

    public PageList(Map<String, Page> pageList) {
        this.pageList = pageList;
    }

    public Map<String, Page> getPageList() {
        return pageList;
    }

    public void setPageList(Map<String, Page> pageList) {
        this.pageList = pageList;
    }
}
