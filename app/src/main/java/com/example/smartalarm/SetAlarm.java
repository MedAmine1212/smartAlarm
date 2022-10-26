package com.example.smartalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartalarm.R;

import java.util.Set;

public class SetAlarm extends AppCompatActivity {
    public static SetAlarm instance;
    AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SetAlarm.instance = SetAlarm.this;
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);


        findViewById(R.id.btnSet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                AlarmDatabase db = Room.databaseBuilder(getApplicationContext(),
//                        AlarmDatabase.class, "database-name").build();

                int time = Integer.parseInt(((EditText)(findViewById(R.id.edtTime))).getText().toString());
                long triggerTime = System.currentTimeMillis()+(time* 1000L);
                Intent idBroadCast = new Intent(SetAlarm.this, MyReciever.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(SetAlarm.this, 1, idBroadCast, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
                Intent intent = new Intent(SetAlarm.this, MainActivity.class);
//                dbHandler.addNewAlarm(time,reqCode, 1);

//                alarmManager.cancel(pendingIntent);

                startActivity(intent);
            }
        });

    }
}