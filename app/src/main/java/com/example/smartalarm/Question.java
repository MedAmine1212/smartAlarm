package com.example.smartalarm;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Question")
public class Question  {
    @PrimaryKey(autoGenerate = true)
    Integer id;

    @ColumnInfo(name = "question")
    private String question;

    @ColumnInfo(name = "option1")
    private String option1;

    @ColumnInfo(name = "option2")
    private String option2;

    @ColumnInfo(name = "option3")
    private String option3;

    @ColumnInfo(name = "answerNr")
    private int answerNr;

    @ColumnInfo(name = "selected")
    private boolean selected;

    public Question() {
    }


    public Question(Integer id, String question, String option1, String option2, String option3, int answerNr, boolean selected) {
        this.id = id;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.answerNr = answerNr;
        this.selected = selected;
    }

    public Question( String question, String option1, String option2, String option3, int answerNr, boolean selected) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.answerNr = answerNr;
        this.selected = selected;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public int getAnswerNr() {
        return answerNr;
    }

    public void setAnswerNr(int answerNr) {
        this.answerNr = answerNr;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}