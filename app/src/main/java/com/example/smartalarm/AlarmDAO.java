package com.example.smartalarm;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface AlarmDAO {

   @Query("select * from alarm")
   List<Alarm> getAlarmsList();

    @Query("select count(id) from alarm")
    Integer getAlarmsCount();

    @Insert
    void addAlarm(Alarm alarm);

    @Update
    void updateAlarm(Alarm alarm);

    @Delete
    void deleteAlarm(Alarm alarm);


    @Query("delete from alarm")
    Integer deleteAll();

    @Query("update alarm set status = 0, repeat = 0")
    void disableAll();

    @Query("select * from alarm where reqId = :id")
    Alarm getAlarmByReqCode(Integer id);
}
