package com.pllug.course.ivankiv.passmanager.data.db;

import android.app.Application;
import android.arch.persistence.room.Room;

public class InitDatabase extends Application {
    public static InitDatabase instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "password_manager_database")
                .allowMainThreadQueries()
                .build();
    }

    public static InitDatabase getInstance() {
        return instance;
    }

    public AppDatabase getDatabese() {
        return database;
    }
}
