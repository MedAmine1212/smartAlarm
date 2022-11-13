package com.example.smartalarm;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "History")
public class History  {
    @PrimaryKey(autoGenerate = true)
    Integer id;
    @ColumnInfo(name = "question")
    private String question;
    @ColumnInfo(name = "answer")
    private int answer;


    public History(String question, int answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
