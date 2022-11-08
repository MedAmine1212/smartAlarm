package com.example.smartalarm;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RingtonesList extends AppCompatActivity {

    int preselected = -1;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ringtones_list);
        AlarmDatabase dbHandler = Room.databaseBuilder(getApplicationContext(),
                AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();

        AppData appData = dbHandler.appDataDAO().getAppData();

        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();
        List<String[]> list = new ArrayList<String[]>();
        while (cursor.moveToNext()) {
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            System.out.println(notificationTitle);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX) + "/" + cursor.getString(RingtoneManager.ID_COLUMN_INDEX);
            Boolean checked = (Objects.equals(appData.ringtoneUri, notificationUri));
             list.add(new String[]{checked.toString(), notificationTitle, notificationUri});
        }
        ListView listview = findViewById(R.id.ringtonesList);
        RingtoneAdapter customAdapter = new RingtoneAdapter(getApplicationContext(), list, this);
        listview.setAdapter(customAdapter);
        listview.setOnItemClickListener((parent, view, position, id) -> {
            try {
                if (mp.isPlaying())
                    mp.stop();
            }catch (Exception ignored) {}
            if(preselected==position ){
                //select the ringtone
                appData.ringtoneUri = list.get(position)[2];
                dbHandler.appDataDAO().updateAppData(appData);
                Toast.makeText(RingtonesList.this, "Alarm ringtone updated !",
                        Toast.LENGTH_LONG).show();
                finish();
                return;
            }
            String[] item = list.get(position);
            System.out.println(item[1]);
            item[0] = "true";
            list.set(position,item);
            if(preselected > -1) {
                String[] oldItem = list.get(preselected);
                oldItem[0] = "false";
                list.set(preselected,oldItem);
            }
            preselected = position;
            customAdapter.notifyDataSetChanged();

            //Play the sound

            mp = MediaPlayer.create(RingtonesList.this, Uri.parse(item[2]));
            mp.setLooping(false);
            mp.start();
        });

        dbHandler.close();
    }
}