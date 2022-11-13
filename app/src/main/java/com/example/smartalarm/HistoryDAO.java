package com.example.smartalarm;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HistoryDAO {

    @Query("select * from History")
    List<History> getHistoryList();

    @Query("select count(id) from History")
    Integer getHistoryCount();

    @Insert
    void addHistory(Alarm History);

    @Update
    void updateHistory(Alarm History);

    @Delete
    void deleteHistory(Alarm History);

    @Query("delete from History")
    Integer deleteAllHistory();


}
