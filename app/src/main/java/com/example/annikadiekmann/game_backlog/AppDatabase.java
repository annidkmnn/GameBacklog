package com.example.annikadiekmann.game_backlog;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Log.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {


    public abstract LogDao logDao();

    private final static String NAME_DATABASE = "game_log";


    //Static instance
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {

        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context, AppDatabase.class, NAME_DATABASE)
                    .build();

        }

        return sInstance;

    }

}


