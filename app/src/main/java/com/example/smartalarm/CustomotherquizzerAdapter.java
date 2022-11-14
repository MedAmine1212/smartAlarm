package com.example.smartalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomotherquizzerAdapter extends BaseAdapter {
Context context;
List<Question> listOtherQuestion;
LayoutInflater inflater;

public CustomotherquizzerAdapter(Context ctx, List<Question> otherQuestionList){

    this.context= ctx;
    this.listOtherQuestion= otherQuestionList;
    inflater=LayoutInflater.from(ctx);
}

    @Override
    public int getCount() {
        return listOtherQuestion.size();
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
    convertView = inflater.inflate(R.layout.other_quizzers,null);
    TextView txtView = (TextView)  convertView.findViewById(R.id.otherQuizzer);
    txtView.setText((CharSequence) listOtherQuestion.get(position).getQuestion());
    return convertView;
    }
}
