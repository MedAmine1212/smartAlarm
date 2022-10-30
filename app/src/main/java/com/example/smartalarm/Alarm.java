package com.example.smartalarm;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarm")
public class Alarm {
    @PrimaryKey(autoGenerate = true)
    Integer id;
    @ColumnInfo(name = "time")
    Integer time;
    @ColumnInfo(name = "status")
    Integer status;
    @ColumnInfo(name = "reqId")
    Integer reqId;

    public Alarm(Integer time, Integer status, Integer reqId) {
        this.reqId = reqId;
        this.time = time;
        this.status = status;
    }
}
