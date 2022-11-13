package com.example.smartalarm.ui.slideshow;

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
            AlarmDatabase dbHandler = Room.databaseBuilder(MainActivity.instance.getApplicationContext(),
                    AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();
            dbHandler.alarmDAO().disableAll();
            Toast.makeText(MainActivity.instance, "All alarms deactivated successfully !",
                    Toast.LENGTH_LONG).show();
            dbHandler.close();
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}