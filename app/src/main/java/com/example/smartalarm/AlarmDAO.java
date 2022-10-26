package com.example.smartalarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;

public interface AlarmDAO {

   @Query("select * from alarm")
    ArrayList<Alarm> getAlarmsList();

    @Query("select count(id) from alarm")
    Integer getAlarmsCount();

    @Insert
    void addAlarm(Alarm alarm);

    @Update
    void updateAlarm(Alarm alarm);

    @Delete
    void deleteAlarm(Alarm alarm);
}
