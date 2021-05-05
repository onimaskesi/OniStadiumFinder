package com.onimaskesi.onistadiumfinder.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.onimaskesi.onistadiumfinder.model.Stadium;
import com.onimaskesi.onistadiumfinder.service.api.StadiumAPIService;
import com.onimaskesi.onistadiumfinder.service.StadiumDao;
import com.onimaskesi.onistadiumfinder.service.StadiumDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MapViewModel extends ViewModel {

    public MutableLiveData stadiumList = new MutableLiveData<List<Stadium>>() ;
    public MutableLiveData isError = new MutableLiveData<Boolean>();

    private StadiumDao stadiumDao;

    StadiumAPIService stadiumAPIService = new StadiumAPIService();

    public void getData(Application application){

        stadiumDao = StadiumDatabase.getDatabase(application).stadiumDao();

        stadiumDao.getAllStadium()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<List<Stadium>>() {
                            @Override
                            public void onSuccess(@NonNull List<Stadium> stadiums) {
                                if(stadiums.isEmpty() || stadiums == null){
                                    getDataFromAPI();
                                } else {
                                    stadiumList.setValue(stadiums);
                                    Log.i("Oni10", "Data come from Room");
                                }

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.i("Oni10", "onError: " + e.getMessage());
                                getDataFromAPI();
                            }
                        }
                );
    }

    private void getDataFromAPI(){

        stadiumAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<ArrayList<Stadium>>() {
                            @Override
                            public void onSuccess(@NonNull ArrayList<Stadium> stadiums) {
                                stadiumList.setValue(stadiums);
                                storeInRoom(stadiums);
                                Log.i("Oni10", "Data come from API");
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.i("Oni10", "onError: " + e.getMessage());
                                isError.setValue(true);
                            }
                        }
                );

    }

    private void storeInRoom(ArrayList<Stadium> stadiums){
        StadiumDatabase.databaseWriteExecutor.execute(() -> {

            for (Stadium stadium : stadiums) {
                insert(stadium);
            }

        });
    }

    private void insert(Stadium stadium) {
        StadiumDatabase.databaseWriteExecutor.execute(() -> {
            stadiumDao.insert(stadium);
        });
    }

}
