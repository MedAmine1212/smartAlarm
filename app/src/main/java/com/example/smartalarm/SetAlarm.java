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
import android.widget.Switch;
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
                idBroadCast.putExtra("reqCode", reqCode);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(SetAlarm.this, reqCode, idBroadCast, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                int repeat = 0;
                if(((Switch)findViewById(R.id.repeatOnOff)).isChecked()) {
                    repeat = 1;
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String timeString = "";
                    if(timePick.getHour() < 10){
                        timeString+="0";
                    }
                    calendar.getTime();
                    timeString+=timePick.getHour()+":";
                    if(timePick.getMinute() < 10){
                        timeString+="0";
                    }
                    timeString+=timePick.getMinute();
                    dbHandler.alarmDAO().addAlarm(new Alarm(calendar.getTimeInMillis(),timeString, 1, repeat, reqCode));
                }

                Intent intent = new Intent(SetAlarm.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}