package com.onimaskesi.onistadiumfinder.service.api;

import com.onimaskesi.onistadiumfinder.model.Stadium;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class StadiumAPIService {

    //URL => https://raw.githubusercontent.com/sorrentmutie/WorldSoccerStadiums/master/SoccerStadiums.json
    //BASE_URL => https://raw.githubusercontent.com/
    //sorrentmutie/WorldSoccerStadiums/master/SoccerStadiums.json

    private String BASE_URL = "https://raw.githubusercontent.com/";
    StadiumAPI api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(StadiumAPI.class);

    public Single<ArrayList<Stadium>> getData(){
        return api.getStadiums();
    }
}
