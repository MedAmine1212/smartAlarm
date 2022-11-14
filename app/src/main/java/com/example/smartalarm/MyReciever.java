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
    static MyReciever instance;
    @Override
    public void onReceive(Context context, Intent intent) {
        instance=this;
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


        //play alarm sound and set sleep time stats

        //sleeping stats***************
        AppData appData = dbHandler.appDataDAO().getAppData();
        Date setDate = new Date(alarm.setAt);

        long duration  = dateNow.getTime() - setDate.getTime();

        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);

        dbHandler.sleepingStatsDAO().addSleepingStats(new SleepingStats(diffInMinutes, alarm.setAt));

        //end sleeping stats*****************************

        //alarm sound
        mp = MediaPlayer.create(context, Uri.parse(appData.ringtoneUri));
        mp.setLooping(true);
        mp.start();

        //Quizz intent here f blaset MainActivity and send notification
        Intent quizIntent = new Intent(context, QuizzActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        quizIntent.putExtra("reqCode", id);
        PendingIntent quizzPeningIntent = PendingIntent.getActivity(context, -1, quizIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "smartAlarmNotifier")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Smart Alarm")
                .setContentText("Alarm is on, click to turn off !")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(quizzPeningIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());

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
        alarm.setAt = dateNow.toString();
        dbHandler.alarmDAO().updateAlarm(alarm);
        dbHandler.close();

    }
}
