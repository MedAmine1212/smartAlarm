package com.example.smartalarm.ui.stats.stats;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.smartalarm.AlarmDatabase;
import com.example.smartalarm.MainActivity;
import com.example.smartalarm.SleepingStats;
import com.example.smartalarm.databinding.FragmentStatsBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StatsFragment extends Fragment {

    private FragmentStatsBinding binding;
    private Handler handler;
    List<SleepingStats> sleepingStats;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StatsViewModel galleryViewModel =
                new ViewModelProvider(this).get(StatsViewModel.class);

        binding = FragmentStatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        handler = new Handler();
//        final TextView textView = binding.textStats;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

//        binding.buttonBarChart.setOnClickListener(view -> {
//            startActivity(new Intent(MainActivity.instance.getApplicationContext(), BarChartActivity.class));
//        });

        AlarmDatabase dbHandler = Room.databaseBuilder(MainActivity.instance.getApplicationContext(),
                AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();
        sleepingStats = dbHandler.sleepingStatsDAO().getStatsForLastMonth();

        if(sleepingStats.size() >0) {
            binding.container.setVisibility(View.VISIBLE);
            startProgress();
            setBarChart();
        }
        else {
            binding.textStats.setText("No data found to generate statistics");
            binding.container.setVisibility(View.GONE);
        }
        return root;
    }

    void setBarChart() {

        BarChart barChart = binding.barChart;
        ArrayList<BarEntry> statsList = new ArrayList<>();
        int x = 1;
        for(SleepingStats stat: sleepingStats) {

            System.out.println(stat);
            statsList.add (new BarEntry( x++,stat.sleptTime/60));
        }

        BarDataSet barDataSet = new BarDataSet(statsList, "Sleeping hours");

        barDataSet.setColors(Color.rgb(137,229,250));
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(11f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisLeft().setDrawLabels(false);

        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.setDrawBorders(false);
        barChart.getDescription().setText("");
        barChart.animateY(2000);

    }

    void startProgress() {

        //progress day
        new Thread(new Runnable() {
            final float sleptDay = ((Float.parseFloat(String.valueOf(sleepingStats.get(0).sleptTime))/1440f)*100);
            int progressStatus = 0;

            @Override
            public void run() {
                while(progressStatus<Math.floor(sleptDay)) {
                    progressStatus+= 5;
                    try{
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //Update progress bar
                    handler.post(() -> {
                        try {
                            binding.thisDay.setProgress(progressStatus);
                            binding.textDay.setText(progressStatus+"%");
                        } catch (Exception ignored){}

                    });
                }
            }
        }).start();

        //progress week
        new Thread(new Runnable() {
            int progressStatus = 0;
            @Override
            public void run() {
                float sleepTime = 0f;
                for(int i=0; i<7;i++) {
                    sleepTime+=sleepingStats.get(i).sleptTime;
                }
                sleepTime = (sleepTime/(1440f*7f))*100f;
                while(progressStatus<Math.floor(sleepTime)) {
                    progressStatus+= 5;
                    try{
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //Update progress bar
                    handler.post(() -> {
                        try {
                            binding.thisWeek.setProgress(progressStatus);
                            binding.textWeek.setText(progressStatus+"%");
                        } catch (Exception ignored){}

                    });
                }
            }
        }).start();

        //progress month
        new Thread(new Runnable() {
            int progressStatus = 0;

            @Override
            public void run() {
                float sleepTime = 0f;
                for(SleepingStats stat: sleepingStats) {
                    sleepTime+=stat.sleptTime;
                }
                sleepTime = (sleepTime/(1440f*30f))*100f;
                while(progressStatus<Math.floor(sleepTime)) {
                    progressStatus+= 5;
                    try{
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //Update progress bar
                    handler.post(() -> {
                        try {
                            binding.thisMonth.setProgress(progressStatus);
                            binding.textMonth.setText(progressStatus+"%");
                        } catch (Exception ignored){}

                    });
                }
            }
        }).start();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}