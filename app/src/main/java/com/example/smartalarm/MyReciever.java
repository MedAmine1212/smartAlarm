package com.example.smartalarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;

public class MyReciever extends BroadcastReceiver {
    MediaPlayer mp;
    
    @Override
    public void onReceive(Context context, Intent intent) {

        int id = intent.getIntExtra("reqCode", 0);

        //Quizz intent here f blaset MainActivity
        Intent activityIntent = new Intent(context, MainActivity.class);
        activityIntent.putExtra("reqCode", id);
        PendingIntent intent2 = PendingIntent.getActivity(context, -1, activityIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "smartAlarmNotifier")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Smart Alarm")
                .setContentText("Alarm is on, click to turn off !")
                .setAutoCancel(false)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(intent2);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());
        mp = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mp.setLooping(true);
        mp.start();

        AlarmDatabase dbHandler = Room.databaseBuilder(context,
                AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();
       Alarm alarm = dbHandler.alarmDAO().getAlarmByReqCode(id);
       if(alarm.repeat == 0) {
           //cancel repeat
           alarm.status = 0;
           dbHandler.alarmDAO().updateAlarm(alarm);
           PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
           AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
           am.cancel(pendingIntent);
       }

    }
}
