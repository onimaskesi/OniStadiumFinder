package com.onimaskesi.onistadiumfinder.service.api;

import com.onimaskesi.onistadiumfinder.model.Stadium;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface StadiumAPI {

    //URL => https://raw.githubusercontent.com/sorrentmutie/WorldSoccerStadiums/master/SoccerStadiums.json
    //BASE_URL => https://raw.githubusercontent.com/
    //sorrentmutie/WorldSoccerStadiums/master/SoccerStadiums.json

    @GET("/sorrentmutie/WorldSoccerStadiums/master/SoccerStadiums.json")
    Single<ArrayList<Stadium>> getStadiums();

}
