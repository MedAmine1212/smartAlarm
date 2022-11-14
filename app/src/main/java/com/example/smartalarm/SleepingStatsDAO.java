package com.example.smartalarm;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface SleepingStatsDAO {



    @Insert
    void addSleepingStats(SleepingStats sleepingStats);

    @Query("delete from sleepingstats")
    void clearSleepingStats();

    @Query("select * from sleepingstats ORDER BY id desc LIMIT 30")
    List<SleepingStats> getStatsForLastMonth();
}
