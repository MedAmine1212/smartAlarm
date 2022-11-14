package com.example.smartalarm;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.room.Room;

import java.io.File;
import java.util.List;

public class CustomquizzerAdapter extends BaseAdapter {
Context context;
List<Question> listQuestion;
LayoutInflater inflater;

public CustomquizzerAdapter(Context ctx, List<Question> questionList){

    this.context= ctx;
    this.listQuestion= questionList;
    inflater=LayoutInflater.from(ctx);
}

    @Override
    public int getCount() {
        return listQuestion.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    convertView = inflater.inflate(R.layout.activity_quiz_list,null);
    TextView txtView = (TextView)  convertView.findViewById(R.id.selectedQuizzer);
    txtView.setText((CharSequence) listQuestion.get(position).getQuestion());

    return convertView;
    }
}
