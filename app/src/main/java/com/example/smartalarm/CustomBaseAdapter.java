package com.example.smartalarm;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.room.Room;

import java.util.List;

public class CustomBaseAdapter extends BaseAdapter {

    Context ctx;
    List<Alarm> alarmList;
    LayoutInflater inflater;
    MainActivity mainActivity;

    public CustomBaseAdapter(Context ctx, List<Alarm> alarmList, MainActivity mainActivity){
        this.ctx = ctx;
        this.alarmList = alarmList;
        inflater = LayoutInflater.from(ctx);
        this.mainActivity = mainActivity;
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

        AlarmDatabase dbHandler = Room.databaseBuilder(ctx,
                AlarmDatabase.class, "alarm_db").allowMainThreadQueries().build();
        convertView = inflater.inflate(R.layout.activity_alarms_list, null);

        convertView.setOnClickListener(view -> {
            Intent intent = new Intent(ctx, SetAlarm.class);
            intent.putExtra("reqId", alarmList.get(position).reqId);
            ctx.startActivity(intent);
        });

        convertView.setOnLongClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
            builder.setCancelable(true);
            builder.setTitle("Delete alarm");
            builder.setMessage("Delete this alarm ?");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // delete and cancel all;
                            int reqId = alarmList.get(position).reqId;
                            Intent intent = new Intent(ctx, MyReciever.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, reqId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
                            am.cancel(pendingIntent);
                            notifyDataSetChanged();
                            dbHandler.alarmDAO().deleteAlarm(alarmList.get(position));
                            alarmList.remove(position);
                            if(alarmList.size() == 0) {
                                ((TextView)MainActivity.instance.findViewById(R.id.text_home)).setText("No Alarms to show");

                            }
                            Toast.makeText(ctx, "Alarm deleted successfully !",
                                    Toast.LENGTH_LONG).show();

                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // close and do nothing
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        });

        TextView txtView = (TextView)convertView.findViewById(R.id.alarmTime);
        Switch swtch = (Switch)convertView.findViewById(R.id.onOffSwitch);
        swtch.setOnClickListener(view -> {
            Alarm alarm = dbHandler.alarmDAO().getAlarmByReqCode(alarmList.get(position).reqId);
            if (swtch.isChecked()) {
                Toast.makeText(ctx, "Alarm activated !",
                        Toast.LENGTH_LONG).show();
                alarm.status = 1;
                alarm.repeat = 1;
            } else {
                Toast.makeText(ctx, "Alarm deactivated !",
                        Toast.LENGTH_LONG).show();
                alarm.repeat = 0;
                alarm.status = 0;
            }
            dbHandler.alarmDAO().updateAlarm(alarm);
        });
        txtView.setText(alarmList.get(position).timeString);
        if(alarmList.get(position).status == 1) {
            swtch.setChecked(true);
        }

        dbHandler.close();
        return convertView;
    }
}
