package com.example.smartalarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuizzActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "extraScore";

    MediaPlayer mp;
    private TextView textViewQuestion;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private Button buttonConfirmNext;
    private Button buttonDisableAlarm;
    private TextView result;
    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;

    private Question currentQuestion;


    AlarmDatabase dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);


        dbHandler = Room.databaseBuilder(getApplicationContext(),
                AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();

        textViewQuestion = findViewById(R.id.text_view_question);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        result = findViewById(R.id.result);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);
        buttonDisableAlarm = findViewById(R.id.button_cancel);

        buttonDisableAlarm.setOnClickListener(view -> {
            MyReciever.instance.mp.stop();
            Intent intent = new Intent(QuizzActivity.this, MainActivity.class);
            startActivity(intent);
        });
        textColorDefaultRb = rb1.getTextColors();

            currentQuestion = dbHandler.questionDAO().getRandomQuestion();
            showQuestion();
    }

    private void showQuestion() {
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();


            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

           buttonConfirmNext.setText("Confirm");

           buttonConfirmNext.setOnClickListener(view -> {
               checkAnswer();
           });

    }

    private void checkAnswer() {

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        History history = new History();

        history.setAnswer(answerNr);
        history.setQuestion(currentQuestion.getQuestion());

        dbHandler.historyDAO().addHistory(history);

        if (answerNr != currentQuestion.getAnswerNr()) {
            result.setText("Wrong answer ! try again");
            result.setTextColor(Color.RED);
        } else {
            MyReciever.instance.mp.stop();
            Intent intent = new Intent(QuizzActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }

    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 1 is correct");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 2 is correct");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 3 is correct");
                break;
        }

            finishQuiz();
    }

    private void finishQuiz() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, 10);
        setResult(RESULT_OK, resultIntent);
        finish();
    }




}