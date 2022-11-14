package com.example.smartalarm;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "appData")
public class AppData {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    @ColumnInfo(name = "ringtoneUri")
    public String ringtoneUri;
    @ColumnInfo(name = "smartAlarm")
    public Boolean smartAlarm;



    public AppData(String ringtoneUri, Boolean smartAlarm) {
        this.ringtoneUri = ringtoneUri;
        this.smartAlarm = smartAlarm;
    }
}
