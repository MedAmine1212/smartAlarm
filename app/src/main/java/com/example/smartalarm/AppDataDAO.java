package com.example.smartalarm;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AppDataDAO {

    @Insert
    void createAppData(AppData appData);

    @Update
    void updateAppData(AppData appData);


    @Query("select * from AppData where id = 1")
    AppData getAppData();
}
