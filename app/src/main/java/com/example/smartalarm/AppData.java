package com.example.smartalarm;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "appData")
public class AppData {
    @PrimaryKey(autoGenerate = true)
    Integer id;
    @ColumnInfo(name = "ringtoneUri")
    String ringtoneUri;
    @ColumnInfo(name = "totalSleepPeriod")
    Long totalSleepPeriod;
    @ColumnInfo(name = "statsStartDate")
    String statsStartDate;



    public AppData(String ringtoneUri, Long totalSleepPeriod, String statsStartDate) {
        this.ringtoneUri = ringtoneUri;
        this.totalSleepPeriod = totalSleepPeriod;
        this.statsStartDate = statsStartDate;
    }
}
