package com.example.smartalarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.smartalarm.databinding.ActivityMainBinding;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    public  static MainActivity instance;
    AlarmDatabase dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainActivity.instance = MainActivity.this;
        super.onCreate(savedInstanceState);
        dbHandler = Room.databaseBuilder(getApplicationContext(),
                AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();
        AppData appData = dbHandler.appDataDAO().getAppData();
//        addSleepingStats();
       try  {
           if (appData == null) {
               createAppData();
           }
       } catch (Exception ignored) {
           createAppData();
       }
       if(dbHandler.questionDAO().getQuestionCount() == 0){
           fillQuestionsTable();
       }
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            createNotificationChannel();
        }
        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SetAlarm.class);
            intent.putExtra("reqId", -1);
            startActivity(intent);

        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_stats)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        dbHandler.close();
    }

    private void createAppData() {
        AppData appData = new AppData(Settings.System.DEFAULT_ALARM_ALERT_URI.toString(), true);
        dbHandler.appDataDAO().createAppData(appData);
    }



    private void fillQuestionsTable() {
        Question q1 = new Question("A is correct", "A", "B", "C", 1, true);
        dbHandler.questionDAO().addQuestion(q1);
        Question q2 = new Question("B is correct", "A", "B", "C", 2, true);
        dbHandler.questionDAO().addQuestion(q2);
        Question q3 = new Question("C is correct", "A", "B", "C", 3,false);
        dbHandler.questionDAO().addQuestion(q3);
        Question q4 = new Question("A is correct again", "A", "B", "C", 1,false);
        dbHandler.questionDAO().addQuestion(q4);
        Question q5 = new Question("B is correct again", "A", "B", "C", 2,false);
        dbHandler.questionDAO().addQuestion(q5);

    }


    private void addSleepingStats() {
        // add virtual sleep time
        SleepingStats s1 = new SleepingStats(480L, "Mon Nov 14 17:55:25 GMT+01:00 2022");
        dbHandler.sleepingStatsDAO().addSleepingStats(s1);
        SleepingStats s2 = new SleepingStats(500L, "Sun Nov 13 17:55:25 GMT+01:00 2022");
        dbHandler.sleepingStatsDAO().addSleepingStats(s2);
        SleepingStats s3 = new SleepingStats(600L, "Mon Nov 07 17:55:25 GMT+01:00 2022");
        dbHandler.sleepingStatsDAO().addSleepingStats(s3);
        SleepingStats s4 = new SleepingStats(320L, "Tue Nov 01 17:55:25 GMT+01:00 2022");
        dbHandler.sleepingStatsDAO().addSleepingStats(s4);
        SleepingStats s5 = new SleepingStats(450L, "Wen Nov 02 17:55:25 GMT+01:00 2022");
        dbHandler.sleepingStatsDAO().addSleepingStats(s5);
        SleepingStats s6 = new SleepingStats(340L, "Thu Nov 03 17:55:25 GMT+01:00 2022");
        dbHandler.sleepingStatsDAO().addSleepingStats(s6);
        SleepingStats s7 = new SleepingStats(220L, "Fri Nov 04 17:55:25 GMT+01:00 2022");
        dbHandler.sleepingStatsDAO().addSleepingStats(s7);
        SleepingStats s8 = new SleepingStats(504L, "Sat Nov 05 17:55:25 GMT+01:00 2022");
        dbHandler.sleepingStatsDAO().addSleepingStats(s8);
        SleepingStats s9 = new SleepingStats(237L, "Sun Nov 06 17:55:25 GMT+01:00 2022");
        dbHandler.sleepingStatsDAO().addSleepingStats(s9);
        SleepingStats s10 = new SleepingStats(334L, "Mon Nov 07 17:55:25 GMT+01:00 2022");
        dbHandler.sleepingStatsDAO().addSleepingStats(s10);
        SleepingStats s11 = new SleepingStats(346L, "Tue Nov 08 17:55:25 GMT+01:00 2022");
        dbHandler.sleepingStatsDAO().addSleepingStats(s11);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createNotificationChannel() {
        CharSequence name = "Smart Alarm";
        String desc = "Alarm is on, click to turn off !";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("smartAlarmNotifier", name, importance);
            channel.setDescription(desc);
        }
    }
}