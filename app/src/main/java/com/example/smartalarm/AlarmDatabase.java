package com.example.smartalarm;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Alarm.class, AppData.class, History.class,Question.class, SleepingStats.class},exportSchema = false, version = 1)
public abstract class AlarmDatabase extends RoomDatabase {
    private static final String BD_NAME = "alarm_db";
    private static AlarmDatabase instance;

    public static synchronized AlarmDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AlarmDatabase.class,
                    BD_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract AlarmDAO alarmDAO();
    public abstract AppDataDAO appDataDAO();
    public abstract HistoryDAO historyDAO();
    public abstract QuestionDAO questionDAO();
    public abstract SleepingStatsDAO sleepingStatsDAO();


}
