package com.example.smartalarm.ui.gallery;

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
import com.example.smartalarm.CustomBaseAdapter;
import com.example.smartalarm.CustomotherquizzerAdapter;
import com.example.smartalarm.CustomquizzerAdapter;
import com.example.smartalarm.MainActivity;
import com.example.smartalarm.Question;
import com.example.smartalarm.databinding.FragmentGalleryBinding;

import java.util.Collections;
import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setGetNoSelectedQuestion();
       setGetSelectedQuestion();
        return root;
    }


    public void setGetNoSelectedQuestion() {
        AlarmDatabase dbHandler = Room.databaseBuilder(MainActivity.instance.getApplicationContext(),
                AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();
        List<Question> NoSelectedQuestion = dbHandler.questionDAO().getNoSelectedQuestion();
        if(NoSelectedQuestion.size() == 0) {
            binding.noNotSelected.setText("No quizzes to show");
        } else {

            Collections.reverse(NoSelectedQuestion);
            ListView listview = binding.selectedQuizzers;
            CustomotherquizzerAdapter customAdapter = new CustomotherquizzerAdapter(MainActivity.instance.getApplicationContext(), NoSelectedQuestion);
            listview.setAdapter(customAdapter);
        }
        dbHandler.close();
    }

    public void setGetSelectedQuestion() {
        AlarmDatabase dbHandler = Room.databaseBuilder(MainActivity.instance.getApplicationContext(),
                AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();
        List<Question> SelectedQuestion = dbHandler.questionDAO().getSelectedQuestion();
        System.out.println(SelectedQuestion.size());
        if(SelectedQuestion.size() == 0) {
            binding.noSelected.setText("No quizzes to show");
        } else {

            Collections.reverse(SelectedQuestion);
            ListView listview = binding.otherquizzers;
            CustomquizzerAdapter customAdapter = new CustomquizzerAdapter(MainActivity.instance.getApplicationContext(), SelectedQuestion);
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