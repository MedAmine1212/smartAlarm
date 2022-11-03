package com.example.smartalarm;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarm")
public class Alarm {
    @PrimaryKey(autoGenerate = true)
    Integer id;
    @ColumnInfo(name = "time")
    Long time;
    @ColumnInfo(name = "timeString")
    String timeString;
    @ColumnInfo(name = "status")
    Integer status;
    @ColumnInfo(name = "repeat")
    Integer repeat;
    @ColumnInfo(name = "reqId")
    Integer reqId;

    public Alarm(Long time,String timeString,Integer status, Integer repeat,Integer reqId) {
        this.reqId = reqId;
        this.time = time;
        this.timeString = timeString;
        this.status = status;
        this.repeat = repeat;
    }
}
