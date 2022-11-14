package com.example.smartalarm;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sleepingstats")
public class SleepingStats {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    @ColumnInfo(name = "sleptTime")
    public Long sleptTime;
    @ColumnInfo(name = "date")
    public String date;

    public SleepingStats(Long sleptTime, String date) {
        this.sleptTime = sleptTime;
        this.date = date;
    }

    @Override
    public String toString() {
        return "SleepingStats{" +
                "id=" + id +
                ", sleptTime=" + sleptTime +
                ", date='" + date + '\'' +
                '}';
    }
}
