package com.example.smartalarm.ui.gallery;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.smartalarm.RingtonesList;
import com.example.smartalarm.databinding.FragmentGalleryBinding;

import java.util.Collections;
import java.util.List;

public class GalleryFragment extends Fragment {

    ListView notSelectedQuizzes;
    ListView selectedQuizzes;
    List<Question> SelectedQuestion;
    List<Question> NoSelectedQuestion;
    CustomquizzerAdapter selectedApadter;
    CustomotherquizzerAdapter nonSelectedApapter;
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
        NoSelectedQuestion = dbHandler.questionDAO().getNoSelectedQuestion();
        if(NoSelectedQuestion.size() == 0) {
            binding.noNotSelected.setText("No quizzes to show");
        } else {

            Collections.reverse(NoSelectedQuestion);
            notSelectedQuizzes = binding.otherquizzers;
            CustomotherquizzerAdapter nonSelectedApapter = new CustomotherquizzerAdapter(MainActivity.instance.getApplicationContext(), NoSelectedQuestion);
            notSelectedQuizzes.setAdapter(nonSelectedApapter);
        }
        dbHandler.close();
    }





    public void setGetSelectedQuestion() {
        AlarmDatabase dbHandler = Room.databaseBuilder(MainActivity.instance.getApplicationContext(),
                AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();
        SelectedQuestion = dbHandler.questionDAO().getSelectedQuestion();
        System.out.println(SelectedQuestion.size());
        if(SelectedQuestion.size() == 0) {
            binding.noSelected.setText("No quizzes to show");
        } else {

            Collections.reverse(SelectedQuestion);
            selectedQuizzes = binding.selectedQuizzers;
            selectedApadter = new CustomquizzerAdapter(MainActivity.instance.getApplicationContext(), SelectedQuestion);
            selectedQuizzes.setAdapter(selectedApadter);
        }
        selectedQuizzes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                   NoSelectedQuestion.add( SelectedQuestion.get(position));
                   SelectedQuestion.remove(position);
                   if(SelectedQuestion.size() == 0)
                       binding.noSelected.setText("No quizzes to show");
                   selectedApadter.notifyDataSetChanged();
               }
           });
        dbHandler.close();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}