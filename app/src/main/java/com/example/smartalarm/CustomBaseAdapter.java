package com.example.smartalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class CustomBaseAdapter extends BaseAdapter {

    Context ctx;
    List<Alarm> alarmList;
    LayoutInflater inflater;

    public CustomBaseAdapter(Context ctx, List<Alarm> alarmList){
        this.ctx = ctx;
        this.alarmList = alarmList;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return this.alarmList.size();
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
        convertView = inflater.inflate(R.layout.activity_alarms_list, null);
        TextView txtView = (TextView)convertView.findViewById(R.id.alarmTime);
        Switch swtch = (Switch)convertView.findViewById(R.id.onOffSwitch);
        txtView.setText(alarmList.get(position).timeString);
        if(alarmList.get(position).status == 1) {
            swtch.setChecked(true);
        }

        return convertView;
    }
}
