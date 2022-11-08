package com.example.smartalarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.room.Room;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.smartalarm.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

public class SetAlarm extends AppCompatActivity {
    public static SetAlarm instance;
    AlarmManager alarmManager;
    Calendar calendar;
    Alarm alarmUp;
    Alarm newAlarm;
    int reqCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SetAlarm.instance = SetAlarm.this;
        setContentView(R.layout.activity_set_alarm);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent thisIntent = getIntent();

        TimePicker timePick = ((TimePicker)findViewById(R.id.edtTime));
        timePick.setIs24HourView(true);

        calendar = Calendar.getInstance();
        int id = thisIntent.getIntExtra("reqId", -1);
        AlarmDatabase dbHandler = Room.databaseBuilder(getApplicationContext(),
                AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();
        if(id != -1) {
            reqCode = id;
            ((TextView)findViewById(R.id.pageTitle)).setText("Update alarm");
            ((Button)findViewById(R.id.btnSet)).setText("UPDATE ALARM");
            alarmUp = dbHandler.alarmDAO().getAlarmByReqCode(reqCode);
            int hour = Integer.parseInt(alarmUp.timeString.substring(0,alarmUp.timeString.indexOf(":")));
            int minute = Integer.parseInt(alarmUp.timeString.substring(alarmUp.timeString.indexOf(":")+1));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePick.setMinute(minute);
                timePick.setHour(hour);
            }
            if(alarmUp.repeat == 1) {
                ((Switch)findViewById(R.id.repeatOnOff)).setChecked(true);
            }
        } else {
            reqCode = dbHandler.alarmDAO().getAlarmsCount()+1;
        }

        findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetAlarm.this, MainActivity.class);
                startActivity(intent);
            }
        });


                findViewById(R.id.btnSet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    newAlarm = new Alarm(calendar.getTimeInMillis(),timeString, 1, repeat, reqCode);
                    if(id == -1) {
                    dbHandler.alarmDAO().addAlarm(newAlarm);
                    Toast.makeText(SetAlarm.this, "Alarm set successfully !",
                            Toast.LENGTH_LONG).show();
                } else {
                    alarmUp.repeat = newAlarm.repeat;
                    alarmUp.status = 1;
                    alarmUp.timeString = newAlarm.timeString;
                    alarmUp.time = newAlarm.time;
                    dbHandler.alarmDAO().updateAlarm(alarmUp);
                    dbHandler.close();
                    Toast.makeText(SetAlarm.this, "Alarm updated successfully !",
                            Toast.LENGTH_LONG).show();
                }
                }
               Intent intent = new Intent(SetAlarm.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}