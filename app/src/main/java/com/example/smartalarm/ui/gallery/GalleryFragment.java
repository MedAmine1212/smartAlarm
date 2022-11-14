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
import com.example.smartalarm.AppData;
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
        AlarmDatabase dbHandler = Room.databaseBuilder(MainActivity.instance.getApplicationContext(),
                AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();
        AppData appData = dbHandler.appDataDAO().getAppData();
        binding.smartOnOff.setChecked(appData.smartAlarm);
        binding.smartOnOff.setOnClickListener(view -> {
            if(binding.smartOnOff.isChecked()){
                Toast.makeText(MainActivity.instance, "Smart alarm is activated !",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.instance, "Smart alarm is deactivated (Lazy) !",
                        Toast.LENGTH_LONG).show();
            }
                appData.smartAlarm = binding.smartOnOff.isChecked();
                dbHandler.appDataDAO().updateAppData(appData);
        });
        setGetNoSelectedQuestion();
       setGetSelectedQuestion();
       dbHandler.close();
        return root;
    }


    public void setGetNoSelectedQuestion() {
        binding.noNotSelected.setText("");
        AlarmDatabase dbHandler = Room.databaseBuilder(MainActivity.instance.getApplicationContext(),
                AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();
        NoSelectedQuestion = dbHandler.questionDAO().getNoSelectedQuestion();
        if(NoSelectedQuestion.size() == 0) {
            binding.noNotSelected.setText("No quizzes to show");
            return;
        } else {

            Collections.reverse(NoSelectedQuestion);
            notSelectedQuizzes = binding.otherquizzers;
            nonSelectedApapter = new CustomotherquizzerAdapter(MainActivity.instance.getApplicationContext(), NoSelectedQuestion);
            notSelectedQuizzes.setAdapter(nonSelectedApapter);
        }
        notSelectedQuizzes.setOnItemClickListener((parent, view, position, id) -> {

            Question qs = NoSelectedQuestion.get(position);
            SelectedQuestion.add(qs);
            qs.setSelected(true);
            dbHandler.questionDAO().updateQuestion(qs);
            NoSelectedQuestion.remove(position);
            nonSelectedApapter.notifyDataSetChanged();
            if(selectedApadter != null)
                selectedApadter.notifyDataSetChanged();
            else {
                setGetSelectedQuestion();
            }
            if(NoSelectedQuestion.size() == 0) {
                binding.noNotSelected.setText("No quizzes to show");
            }
        });
        dbHandler.close();
    }

    public void setGetSelectedQuestion() {
        binding.noSelected.setText("");
        AlarmDatabase dbHandler = Room.databaseBuilder(MainActivity.instance.getApplicationContext(),
                AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();
        SelectedQuestion = dbHandler.questionDAO().getSelectedQuestion();
        System.out.println(SelectedQuestion.size());
        if(SelectedQuestion.size() == 0) {
            binding.noSelected.setText("No quizzes to show");
            return;
        } else {

            Collections.reverse(SelectedQuestion);
            selectedQuizzes = binding.selectedQuizzers;
            selectedApadter = new CustomquizzerAdapter(MainActivity.instance.getApplicationContext(), SelectedQuestion);
            selectedQuizzes.setAdapter(selectedApadter);
        }
        selectedQuizzes.setOnItemClickListener((parent, view, position, id) -> {

            if(SelectedQuestion.size() == 1) {
                Toast.makeText(MainActivity.instance, "Minimum 1 quizz selected is required",
                        Toast.LENGTH_LONG).show();
                return;
            }
            Question qs = SelectedQuestion.get(position);
            NoSelectedQuestion.add( qs);
            qs.setSelected(false);
            dbHandler.questionDAO().updateQuestion(qs);
            SelectedQuestion.remove(position);
            selectedApadter.notifyDataSetChanged();
            if(nonSelectedApapter != null)
                nonSelectedApapter.notifyDataSetChanged();
            else {
                setGetNoSelectedQuestion();
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