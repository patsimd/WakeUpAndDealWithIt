package al.demo.alarmmanagerdemo.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import al.demo.alarmmanagerdemo.AlarmHelper;
import al.demo.alarmmanagerdemo.DatabaseHelper;
import al.demo.alarmmanagerdemo.MainActivity;
import al.demo.alarmmanagerdemo.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Utilisateur on 2018-04-01.
 */

public class setAlarmFragment extends Fragment{



    private static AlarmHelper alarmHelper;

    private TextView alarmStatusTextView;
    private TextView alarmNameTextView;
    private Spinner spinnerDifficulty;
    private CheckBox repeatCheckBox;
    private int s_hour;
    private int s_minute;
    private Calendar selectedTime;
    private Uri alarmMusic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle bundle){
        View v = inflater.inflate(R.layout.set_alarm,vg,false);


        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        alarmHelper = new AlarmHelper(this.getContext());
        alarmMusic = RingtoneManager.getActualDefaultRingtoneUri(getContext(), RingtoneManager.TYPE_ALARM);

        alarmStatusTextView = (TextView) view.findViewById(R.id.status_text_view);
        alarmNameTextView = (TextView)view.findViewById(R.id.alarmName);
        spinnerDifficulty = (Spinner)view.findViewById(R.id.alarmDifficultySpinner);
        repeatCheckBox = (CheckBox)view.findViewById(R.id.alarmRepeatCheckBox);

        view.findViewById(R.id.schedule_notification_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent notificationIntent = new Intent("al.demo.alarmmanagerdemo.NOTIFY_ACTION");
                String alarmLabel = alarmNameTextView.getText().toString();
                if(alarmLabel.isEmpty())
                    alarmLabel = "Alarm!";

                notificationIntent.putExtra("AlarmName",alarmLabel);
                notificationIntent.putExtra("musicUri",alarmMusic.toString());
                notificationIntent.putExtra("difficultyString", spinnerDifficulty.getSelectedItem().toString());

                //long temp = Calendar.getInstance().getTimeInMillis() + 10;
                selectedTime.set(Calendar.MONTH,Calendar.getInstance().get(Calendar.MONTH));
                selectedTime.set(Calendar.DAY_OF_MONTH,Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                if (selectedTime.before(Calendar.getInstance())) {
                    if(!(selectedTime.get(Calendar.HOUR_OF_DAY) == Calendar.getInstance().get(Calendar.HOUR_OF_DAY) && selectedTime.get(Calendar.MINUTE) == Calendar.getInstance().get(Calendar.MINUTE)))
                        selectedTime.add(Calendar.DAY_OF_MONTH, 1);
                }

                long id = MainActivity.dbHelper.addAlarm(alarmLabel,selectedTime.getTime().getHours(),selectedTime.getTime().getMinutes(),true,spinnerDifficulty.getSelectedItem().toString(),repeatCheckBox.isChecked(),alarmMusic.toString());

                notificationIntent.putExtra("alarmID",(int)id);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), (int)id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                //selectedTime = Calendar.getInstance();
                //selectedTime.add(Calendar.MINUTE, 1);


                alarmHelper.schedulePendingIntent(selectedTime.getTimeInMillis(), pendingIntent);

                Log.i("CHECKCA",String.valueOf(selectedTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()));
                //alarmHelper.schedulePendingIntent(Calendar.getInstance().getTimeInMillis() + 5000, pendingIntent);

                long timelapse = selectedTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
                alarmStatusTextView.setText("Called in " + timelapse / 1000 + " seconds" + "   Day : " + selectedTime.get(Calendar.DAY_OF_MONTH));

                alarmNameTextView.setText("");

                //TOAST

                Calendar timeFromNow = Calendar.getInstance();
                timeFromNow.set(Calendar.MINUTE,selectedTime.get(Calendar.MINUTE));
                timeFromNow.set(Calendar.HOUR_OF_DAY,selectedTime.get(Calendar.HOUR_OF_DAY));

                timeFromNow.add(Calendar.HOUR_OF_DAY,-Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                timeFromNow.add(Calendar.MINUTE,-Calendar.getInstance().get(Calendar.MINUTE));

                int intHours = timeFromNow.get(Calendar.HOUR_OF_DAY);
                String hours;

                if(intHours != 0)
                    hours = String.valueOf(intHours) + " hours and ";
                else
                    hours = "";
                String minutes = String.valueOf(timeFromNow.get(Calendar.MINUTE));
                //display in long period of time
                Toast.makeText(getContext(), "Alarm set in " + hours + minutes + " minutes from now", Toast.LENGTH_LONG).show();

            }
        });


        TimePicker timePicker = (TimePicker)view.findViewById(R.id.alarmTimeSelector);
        selectedTime = Calendar.getInstance();
        selectedTime.set(Calendar.SECOND, 0);
        //selectedTime.add(Calendar.DAY_OF_MONTH, 1);

        timePicker.setHour(selectedTime.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(selectedTime.get(Calendar.MINUTE));

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                selectedTime.set(Calendar.MINUTE, minute);
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                selectedTime.set(Calendar.SECOND, 0);

                //alarmStatusTextView.setText("Time: " + selectedTime.get(Calendar.HOUR_OF_DAY) + ":" + selectedTime.get(Calendar.MINUTE));
            }
        });


        view.findViewById(R.id.ButtonChooseMusic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Alarm Sound");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI,  alarmMusic);
                startActivityForResult(intent, 10);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK && requestCode == 10){
            Uri tempUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if(tempUri != null)
                alarmMusic = tempUri;
        }
    }
}
