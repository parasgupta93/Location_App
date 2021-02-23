package com.example.myapplication.roomdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface GetLocationDao {

    @Delete()
    void delete(LocationModel bean);


    @Query("SELECT * FROM Locations")
    List<LocationModel> loadAllItem();


    @Query("DELETE FROM Locations")
    void clearData();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LocationModel bean);
}
