package com.onimaskesi.onistadiumfinder.service;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.onimaskesi.onistadiumfinder.model.Stadium;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Stadium.class}, version = 1, exportSchema = false)
public abstract class StadiumDatabase extends RoomDatabase {

    public abstract StadiumDao stadiumDao();

    private static volatile StadiumDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static StadiumDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StadiumDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            StadiumDatabase.class, "stadium_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
