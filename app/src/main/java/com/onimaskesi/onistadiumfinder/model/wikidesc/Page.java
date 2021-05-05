package com.onimaskesi.onistadiumfinder.model.wikidesc;

import com.google.gson.annotations.SerializedName;

public class Page {
    @SerializedName("title")
    String title;

    @SerializedName("extract")
    String description;

    public Page(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
