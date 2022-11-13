package com.example.smartalarm;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class History implements Parcelable {
    private String question;
    private int answer;

    protected History(Parcel in) {
        question = in.readString();
        answer = in.readInt();
    }
    protected History() {

    }
    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeInt(answer);
    }

    public String getQuestion() {
        return question;
    }

    public int getAnswer() {
        return answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
