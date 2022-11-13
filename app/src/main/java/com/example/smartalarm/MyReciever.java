package com.example.smartalarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MyReciever extends BroadcastReceiver {
    MediaPlayer mp;
    
    @Override
    public void onReceive(Context context, Intent intent) {

        int id = intent.getIntExtra("reqCode", 0);

        Date dateNow = new Date();

        AlarmDatabase dbHandler = Room.databaseBuilder(context,
                AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();
        Alarm alarm = dbHandler.alarmDAO().getAlarmByReqCode(id);
        System.out.println("status: "+alarm.status);
        if(alarm.status == 0) {
            //update setTime +1 day and return
            alarm.setAt = dateNow.toString();
            dbHandler.alarmDAO().updateAlarm(alarm);
            dbHandler.close();
            return;
        }
        System.out.println("Running");
        //Quizz intent here f blaset MainActivity and send notification
        Intent activityIntent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activityIntent.putExtra("reqCode", id);
        PendingIntent intent2 = PendingIntent.getActivity(context, -1, activityIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "smartAlarmNotifier")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Smart Alarm")
                .setContentText("Alarm is on, click to turn off !")
                .setAutoCancel(false)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(intent2);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());


        //play alarm sound and set sleep time stats
        AppData appData = dbHandler.appDataDAO().getAppData();
        Date setDate = new Date(alarm.setAt);

        long duration  = dateNow.getTime() - setDate.getTime();

        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        System.out.println(diffInMinutes);
        mp = MediaPlayer.create(context, Uri.parse(appData.ringtoneUri));
        mp.setLooping(true);
        mp.start();

        //update setTime +1 day
        alarm.setAt = dateNow.toString();
        //get the alarm and check if should be repeated or canceled
       if(alarm.repeat == 0) {
           //cancel repeat
           alarm.status = 0;
           PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
           AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
           am.cancel(pendingIntent);
       }
        dbHandler.alarmDAO().updateAlarm(alarm);
        dbHandler.close();

    }
}
