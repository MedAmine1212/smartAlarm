package com.example.smartalarm.ui.home;

import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.smartalarm.Alarm;
import com.example.smartalarm.AlarmDatabase;
import com.example.smartalarm.CustomBaseAdapter;
import com.example.smartalarm.MainActivity;
import com.example.smartalarm.R;

import java.util.Collections;
import java.util.List;

public class HomeViewModel extends ViewModel {

        private final MutableLiveData<String> mText;

        public HomeViewModel() {
            mText = new MutableLiveData<>();
        }

        public LiveData<String> getText() {
            return mText;
        }
    }