package al.demo.alarmmanagerdemo.fragment;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.List;

import al.demo.alarmmanagerdemo.AlarmHelper;
import al.demo.alarmmanagerdemo.DatabaseHelper;
import al.demo.alarmmanagerdemo.MainActivity;
import al.demo.alarmmanagerdemo.R;

/**
 * Created by Utilisateur on 2018-04-01.
 */

public class setAlarmFragment extends Fragment{



    private AlarmHelper alarmHelper;

    private TextView alarmStatusTextView;
    private TextView alarmNameTextView;
    private int s_hour;
    private int s_minute;
    private Calendar selectedTime;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle bundle){
        View v = inflater.inflate(R.layout.set_alarm,vg,false);


        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        alarmHelper = new AlarmHelper(this.getContext());

        alarmStatusTextView = (TextView) view.findViewById(R.id.status_text_view);
        alarmNameTextView = (TextView)view.findViewById(R.id.alarmName);
        view.findViewById(R.id.schedule_notification_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent notificationIntent = new Intent("al.demo.alarmmanagerdemo.NOTIFY_ACTION");
                if(alarmNameTextView.getText().toString().isEmpty())
                    notificationIntent.putExtra("AlarmName","Alarm!");
                else
                    notificationIntent.putExtra("AlarmName",alarmNameTextView.getText().toString());
                //long temp = Calendar.getInstance().getTimeInMillis() + 10;

                long id = MainActivity.dbHelper.addAlarm(alarmNameTextView.getText().toString(),Calendar.getInstance().getTime().getHours(),Calendar.getInstance().getTime().getMinutes() + 1,1,"easy",true);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), (int)id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                //selectedTime = Calendar.getInstance();
                //selectedTime.add(Calendar.MINUTE, 1);


                //alarmHelper.schedulePendingIntent(selectedTime.getTimeInMillis(), pendingIntent);
                alarmHelper.schedulePendingIntent(Calendar.getInstance().getTimeInMillis() + 10, pendingIntent);

                long timelapse = selectedTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
                alarmStatusTextView.setText("Called in " + timelapse / 1000 + " seconds");
            }
        });


        TimePicker timePicker = (TimePicker)view.findViewById(R.id.alarmTimeSelector);
        selectedTime = Calendar.getInstance();
        selectedTime.set(Calendar.SECOND, 0);

        timePicker.setHour(selectedTime.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(selectedTime.get(Calendar.MINUTE));

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                selectedTime.set(Calendar.MINUTE, minute);
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                selectedTime.set(Calendar.SECOND, 0);
                alarmStatusTextView.setText("time: " +selectedTime.get(Calendar.HOUR_OF_DAY)+ ":" + selectedTime.get(Calendar.MINUTE));
            }
        });

    }

}
