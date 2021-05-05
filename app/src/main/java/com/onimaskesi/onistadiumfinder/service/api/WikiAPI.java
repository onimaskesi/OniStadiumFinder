package com.onimaskesi.onistadiumfinder.service.api;

import com.onimaskesi.onistadiumfinder.model.wikidesc.WikiDescResponse;
import com.onimaskesi.onistadiumfinder.model.wikisearch.QueryWS;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikiAPI {

    // https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro&explaintext&redirects=1
    @GET("/w/api.php")
    Single<WikiDescResponse> getWikiDesc(
            @Query("format") String format,
            @Query("action") String action,
            @Query("prop") String prop,
            @Query("exintro") String exintro,
            @Query("explaintext") String explaintext,
            @Query("redirects") int redirects,
            @Query("pageids") int pageid
    );

    @GET("/w/api.php")
    Single<QueryWS> getWikiSearches(
            @Query("action")String action,
            @Query("list")String list,
            @Query("srsearch")String search,
            @Query("format")String format
    );

}
