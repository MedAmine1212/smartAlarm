package com.example.smartalarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.smartalarm.R;

import java.util.Calendar;
import java.util.Set;

public class SetAlarm extends AppCompatActivity {
    public static SetAlarm instance;
    AlarmManager alarmManager;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SetAlarm.instance = SetAlarm.this;
       alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);
        TimePicker timePick = ((TimePicker)findViewById(R.id.edtTime));
        timePick.setIs24HourView(true);



        findViewById(R.id.btnSet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlarmDatabase dbHandler = Room.databaseBuilder(getApplicationContext(),
                        AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();

                int reqCode = dbHandler.alarmDAO().getAlarmsCount()+1;
                calendar = Calendar.getInstance();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    calendar.set(Calendar.HOUR_OF_DAY,timePick.getHour());
                    calendar.set(Calendar.MINUTE,timePick.getMinute());
                    calendar.set(Calendar.SECOND,0);
                    calendar.set(Calendar.MILLISECOND,0);

                }
                Intent idBroadCast = new Intent(SetAlarm.this, MyReciever.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(SetAlarm.this, reqCode, idBroadCast, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                Intent intent = new Intent(SetAlarm.this, MainActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    dbHandler.alarmDAO().addAlarm(new Alarm(calendar.getTimeInMillis(),timePick.getHour()+":"+timePick.getMinute(), 1, reqCode));
                }

                startActivity(intent);
            }
        });

    }
}