package com.example.smartalarm.ui.slideshow;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.smartalarm.AlarmDatabase;
import com.example.smartalarm.CustomBaseAdapter;
import com.example.smartalarm.MainActivity;
import com.example.smartalarm.MyReciever;
import com.example.smartalarm.R;
import com.example.smartalarm.RingtoneAdapter;
import com.example.smartalarm.SetAlarm;
import com.example.smartalarm.databinding.FragmentSlideshowBinding;
import com.example.smartalarm.RingtonesList;

import java.util.HashMap;
import java.util.Map;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        binding.soundSettings.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.instance, RingtonesList.class);
            startActivity(intent);
        });
        binding.disableAll.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(inflater.getContext());
            builder.setCancelable(true);
            builder.setTitle("Disable all alarms");
            builder.setMessage("Are you sure you want to disable all alarms ?");
            builder.setPositiveButton("Confirm",
                    (dialog, which) -> {
                        // disable all alarms;
                        AlarmDatabase dbHandler = Room.databaseBuilder(MainActivity.instance.getApplicationContext(),
                                AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();
                        dbHandler.alarmDAO().disableAll();
                        Toast.makeText(MainActivity.instance, "All alarms deactivated successfully !",
                                Toast.LENGTH_LONG).show();
                        dbHandler.close();

                    });
            builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                // close and do nothing
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        binding.resetStats.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(inflater.getContext());
            builder.setCancelable(true);
            builder.setTitle("Reset sleeping stats");
            builder.setMessage("Are you sure you want to reset all sleeping stats ?");
            builder.setPositiveButton("Confirm",
                    (dialog, which) -> {
                        // reset sleeping stats;
                        AlarmDatabase dbHandler = Room.databaseBuilder(MainActivity.instance.getApplicationContext(),
                                AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();
                        dbHandler.sleepingStatsDAO().clearSleepingStats();
                        Toast.makeText(MainActivity.instance, "Sleeping stats reset successfully",
                                Toast.LENGTH_LONG).show();
                        dbHandler.close();
                    });
            builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                // close and do nothing
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        binding.resetQuizzes.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(inflater.getContext());
            builder.setCancelable(true);
            builder.setTitle("Reset quizzes preferences");
            builder.setMessage("Are you sure you want to reset quizzes preferences ?");
            builder.setPositiveButton("Confirm",
                    (dialog, which) -> {
                        // reset quizzes stats;

                    });
            builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                // close and do nothing
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}