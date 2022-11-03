package com.example.smartalarm;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyReciever extends BroadcastReceiver {
    MediaPlayer mp;
    Vibrator vib;
    
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "smartAlarm")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Smart Alarm")
                .setContentText("Alarm is on click to turn off !")
                .setAutoCancel(false)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());
        mp = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mp.setLooping(true);
        mp.start();
    }
}
