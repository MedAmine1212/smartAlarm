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
        try {
            if (appData == null) {
                createAppData();
            }
        } catch (Exception ignored) {
            createAppData();
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
        AppData appData = new AppData(Settings.System.DEFAULT_ALARM_ALERT_URI.toString(), 0L, new Date().toString());
        dbHandler.appDataDAO().createAppData(appData);
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