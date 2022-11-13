package com.example.smartalarm.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.smartalarm.Alarm;
import com.example.smartalarm.AlarmDatabase;
import com.example.smartalarm.AlarmsList;
import com.example.smartalarm.CustomBaseAdapter;
import com.example.smartalarm.MainActivity;
import com.example.smartalarm.R;
import com.example.smartalarm.databinding.FragmentHomeBinding;

import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        setAlarmsList();
        return root;
    }
    public void setAlarmsList() {
        AlarmDatabase dbHandler = Room.databaseBuilder(MainActivity.instance.getApplicationContext(),
                AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();
        List<Alarm> alarmList = dbHandler.alarmDAO().getAlarmsList();
        if(alarmList.size() == 0) {
            ((TextView)MainActivity.instance.findViewById(R.id.text_home)).setText("No Alarms to show");
        } else {

            Collections.reverse(alarmList);
            ListView listview = binding.alarmsList;
            CustomBaseAdapter customAdapter = new CustomBaseAdapter(MainActivity.instance.getApplicationContext(), alarmList, MainActivity.instance);
            listview.setAdapter(customAdapter);
        }
        dbHandler.close();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}