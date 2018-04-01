package al.demo.alarmmanagerdemo;

import android.annotation.SuppressLint;
//import android.icu.util.Calendar;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private AlarmHelper alarmHelper;

    private TextView alarmStatusTextView;
    private int s_hour;
    private int s_minute;
    private Calendar selectedTime;// = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmHelper = new AlarmHelper(this);

        alarmStatusTextView = (TextView) findViewById(R.id.status_text_view);


        findViewById(R.id.schedule_notification_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent notificationIntent = new Intent("al.demo.alarmmanagerdemo.NOTIFY_ACTION");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 1234, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                //selectedTime = Calendar.getInstance();
                //selectedTime.add(Calendar.MINUTE, 1);
                alarmHelper.schedulePendingIntent(selectedTime.getTimeInMillis(), pendingIntent);

                long timelapse = selectedTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
                alarmStatusTextView.setText("Called in " + timelapse / 1000 + " seconds");
            }
        });


        TimePicker timePicker = (TimePicker)findViewById(R.id.alarmTimeSelector);
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
