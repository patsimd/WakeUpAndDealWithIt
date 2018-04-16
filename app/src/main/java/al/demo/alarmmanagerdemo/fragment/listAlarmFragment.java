package al.demo.alarmmanagerdemo.fragment;

import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import al.demo.alarmmanagerdemo.AlarmHelper;
import al.demo.alarmmanagerdemo.AlarmPlayer;
import al.demo.alarmmanagerdemo.DatabaseHelper;
import al.demo.alarmmanagerdemo.MainActivity;
import al.demo.alarmmanagerdemo.R;

/**
 * Created by Utilisateur on 2018-04-01.
 */

public class listAlarmFragment extends Fragment{


    LinearLayout baseList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle bundle){
        View v = inflater.inflate(R.layout.list_alarm,vg,false);
        final Cursor c = MainActivity.dbHelper.getAlarm();
        baseList = (LinearLayout) v.findViewById(R.id.baseList);





        showAlarm(c);
        return v;
    }

    public void showAlarm(Cursor c) {
        if (baseList != null) {

            baseList.removeAllViews();
            while (c.moveToNext()) {
                Log.i("ReadAlarm", String.valueOf(c.getInt(0)));

                LinearLayout layoutLine1 = new LinearLayout(getContext());
                layoutLine1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,225));
                layoutLine1.setOrientation(LinearLayout.HORIZONTAL);
                layoutLine1.setGravity(Gravity.CENTER);

                ImageView iv = new ImageView(getContext());
                iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.5f));
                iv.setImageResource(R.mipmap.ic_launcher);
               // iv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);



                LinearLayout leftContainer = new LinearLayout(getContext());
                leftContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.1f));
                leftContainer.setOrientation(LinearLayout.VERTICAL);
                leftContainer.addView(iv);


                layoutLine1.addView(leftContainer);

                TextView tvTime = new TextView(getContext());


                tvTime.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.50f));
                tvTime.setText(String.format("%02d",c.getInt(2)) + ":" + String.format("%02d",c.getInt(3)));
                tvTime.setSingleLine();
                tvTime.setPadding(30,0,0,0);
                if(c.getInt(4) == 1)
                    tvTime.setTextColor(Color.BLACK);
                else
                    tvTime.setTextColor(Color.GRAY);

                tvTime.setTextSize(28);
                layoutLine1.addView(tvTime);

                LinearLayout container2 = new LinearLayout(getContext());
                container2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.4f));
                container2.setOrientation(LinearLayout.VERTICAL);
                container2.setGravity(Gravity.CENTER);



                LinearLayout rangertop = new LinearLayout(getContext());
                rangertop.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
                rangertop.setOrientation(LinearLayout.HORIZONTAL);
                rangertop.setGravity(Gravity.BOTTOM);


                LinearLayout rangerbot = new LinearLayout(getContext());
                rangerbot.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,2));
                rangerbot.setOrientation(LinearLayout.HORIZONTAL);
                rangerbot.setGravity(Gravity.CENTER);


                container2.addView(rangerbot);
                container2.addView(rangertop);
                layoutLine1.addView(container2);

                Switch swEnable = new Switch(getContext());
                swEnable.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));
                if(c.getInt(4) == 1)
                    swEnable.setChecked(true);
                else
                    swEnable.setChecked(false);
                swEnable.setTag(c.getInt(0));
                swEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton switchView, boolean isChecked) {
                        AlarmHelper alarmHelper = new AlarmHelper(getContext());
                        if(isChecked){
                            MainActivity.dbHelper.updateAlarmEnable((int) switchView.getTag(),true);
                            Cursor singleCursor = MainActivity.dbHelper.getAlarmByID((int)switchView.getTag());
                            Intent notificationIntent = new Intent("al.demo.alarmmanagerdemo.NOTIFY_ACTION");

                            try {
                                if (singleCursor.moveToFirst()) {
                                    notificationIntent.putExtra("AlarmName",singleCursor.getString(1));
                                    notificationIntent.putExtra("musicUri",singleCursor.getString(7));
                                    notificationIntent.putExtra("difficultyString", singleCursor.getString(5));
                                    notificationIntent.putExtra("alarmID",singleCursor.getInt(0));

                                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), (int)switchView.getTag(), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                                    Calendar selectedTime = Calendar.getInstance();
                                    selectedTime.set(Calendar.MINUTE, singleCursor.getInt(3));
                                    selectedTime.set(Calendar.HOUR_OF_DAY, singleCursor.getInt(2));
                                    selectedTime.set(Calendar.SECOND, 0);
                                    if (selectedTime.before(Calendar.getInstance())) {
                                        if(!(selectedTime.get(Calendar.HOUR_OF_DAY) == Calendar.getInstance().get(Calendar.HOUR_OF_DAY) && selectedTime.get(Calendar.MINUTE) == Calendar.getInstance().get(Calendar.MINUTE)))
                                            selectedTime.add(Calendar.DAY_OF_MONTH, 1);
                                    }
                                    alarmHelper.schedulePendingIntent(selectedTime.getTimeInMillis(), pendingIntent);

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
                            } finally {
                                singleCursor.close();
                            }


                        }
                        else{
                            alarmHelper.unschedulePendingIntent((int) switchView.getTag());
                            MainActivity.dbHelper.updateAlarmEnable((int) switchView.getTag(),false);
                        }
                        MainActivity.updateAlarmList();
                    }


                });
                leftContainer.addView(swEnable);



                TextView tvLabel = new TextView(getContext());
                tvLabel.setText(c.getString(1));
                tvLabel.setSingleLine();
                tvLabel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                if(c.getInt(4) == 1)
                    tvLabel.setTextColor(Color.BLACK);
                else
                    tvLabel.setTextColor(Color.GRAY);
                tvLabel.setTextSize(18);
                rangerbot.addView(tvLabel);

              TextView tvDifficulty = new TextView(getContext());
                tvDifficulty.setText(c.getString(5));

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f);
                lp.gravity = Gravity.CENTER;
                tvDifficulty.setPadding(40,40,0,0);
                tvDifficulty.setLayoutParams(lp);

                if(c.getInt(4) == 1)
                    tvDifficulty.setTextColor(Color.BLACK);
                else
                    tvDifficulty.setTextColor(Color.GRAY);
                tvDifficulty.setTextSize(14);
               rangertop.addView(tvDifficulty);



                Button bDelete = new Button(getContext());
                bDelete.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f));
                bDelete.setText("Delete");
                bDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlarmHelper alarmHelper = new AlarmHelper(getContext());
                        alarmHelper.unschedulePendingIntent((int) v.getTag());
                        MainActivity.dbHelper.deleteArlarm((int) v.getTag());
                        showAlarm(MainActivity.dbHelper.getAlarm());
                    }
                });
                bDelete.setTag(c.getInt(0));
                rangertop.addView(bDelete);

                View smallLine = new View(getContext());
                smallLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,5));
                smallLine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.kindOfGrey));

                baseList.addView(layoutLine1);
                baseList.addView(smallLine);



            }
            c.close();
        }
    }

}