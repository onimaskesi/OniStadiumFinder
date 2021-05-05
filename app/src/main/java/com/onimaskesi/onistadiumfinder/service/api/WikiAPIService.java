package com.onimaskesi.onistadiumfinder.service.api;

import android.util.Log;

import com.onimaskesi.onistadiumfinder.model.wikidesc.Page;
import com.onimaskesi.onistadiumfinder.model.wikidesc.WikiDescResponse;
import com.onimaskesi.onistadiumfinder.model.wikisearch.QueryWS;
import com.onimaskesi.onistadiumfinder.model.wikisearch.WikiSearch;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WikiAPIService {

    String TAG = "Oni10";

    private String BASE_URL = "https://en.wikipedia.org/";
    WikiAPI api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(WikiAPI.class);

    //TODO: return değeri Page
    public void getData(String searchText){

        Single<QueryWS> searchResponse = api.getWikiSearches("query", "search", searchText,  "json");

        searchResponse
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<QueryWS>() {
                    @Override
                    public void onSuccess(@NonNull QueryWS queryWS) {

                        List<WikiSearch> wikiSearchResultsList = queryWS.getWikiSearchList().getSearchList();
                        if(wikiSearchResultsList != null && !wikiSearchResultsList.isEmpty()){
                            int pageid = wikiSearchResultsList.get(0).getPageid();
                            Single<WikiDescResponse> descResponse = api.getWikiDesc("json","query","extracts","","",1, pageid);

                            descResponse
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeWith(new DisposableSingleObserver<WikiDescResponse>(){
                                        @Override
                                        public void onSuccess(@NonNull WikiDescResponse wikiDescResponse) {

                                            Page page = wikiDescResponse.getQuery().getPageList().get(String.valueOf(pageid));

                                            Log.i(TAG, "Page title: " + page.getTitle());
                                        }

                                        @Override
                                        public void onError(@NonNull Throwable e) {
                                            Log.i(TAG, "onError: descResponse : " + e.getMessage());
                                        }
                                    });

                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: searchResponse : " + e.getMessage());
                    }
                });

    }

}
