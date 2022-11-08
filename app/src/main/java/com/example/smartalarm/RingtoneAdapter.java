package com.example.smartalarm;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.room.Room;

import java.util.List;
import java.util.Map;

public class RingtoneAdapter extends BaseAdapter {

    Context ctx;
    List<String[]> list;
    LayoutInflater inflater;
    RingtonesList ringtonesList;

    public RingtoneAdapter(Context ctx, List<String[]> list, RingtonesList ringtonesList){
        this.ctx = ctx;
        this.list = list;
        inflater = LayoutInflater.from(ctx);
        this.ringtonesList = ringtonesList;
    }


    @Override
    public int getCount() {
        return this.list.size();
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

        convertView = inflater.inflate(R.layout.activity_ringtones_list, null);


        TextView txtView = (TextView)convertView.findViewById(R.id.ringtoneTitle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtView.setText((CharSequence) list.get(position)[1]);
        }
        CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.checkThis);
        checkBox.setChecked(list.get(position)[0].equals("true"));
        return convertView;
    }
}
