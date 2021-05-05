package com.onimaskesi.onistadiumfinder.service;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.onimaskesi.onistadiumfinder.model.Stadium;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

@Dao
public interface StadiumDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Stadium stadium);

    @Query("SELECT * FROM Stadium")
    Single<List<Stadium>> getAllStadium();

    @Query("SELECT * FROM Stadium WHERE Id = :stadiumId")
    Stadium getStadium(int stadiumId);

}
