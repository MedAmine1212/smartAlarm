package com.example.smartalarm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    public  static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainActivity.instance = MainActivity.this;
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            createNotificationChannel();
//        }
        showAlarmList();
        createNotificationChannel();
        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SetAlarm.class);
            startActivity(intent);

        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void showAlarmList() {
        AlarmDatabase dbHandler = Room.databaseBuilder(getApplicationContext(),
                AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();
        List<Alarm> alarmList = dbHandler.alarmDAO().getAlarmsList();
        ListView listview = findViewById(R.id.alarmsList);
        CustomBaseAdapter customAdapter = new CustomBaseAdapter(getApplicationContext(), alarmList);
        listview.setAdapter(customAdapter);

    }

    private void createNotificationChannel() {
        CharSequence name = "Smart Alarm";
        String desc = "Alarm is on, click to turn off !";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("smartAlarmNotifier", name, importance);
            channel.setDescription(desc);
        }
    }
}