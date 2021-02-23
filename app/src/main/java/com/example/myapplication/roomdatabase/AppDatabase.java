package com.example.myapplication.roomdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {LocationModel.class},
        version = 1, exportSchema = false)
abstract  class Appdatabase extends RoomDatabase {

    abstract GetLocationDao getLocationDao();

}

