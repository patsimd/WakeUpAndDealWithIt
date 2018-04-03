package al.demo.alarmmanagerdemo.fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
                LinearLayout newLayout = new LinearLayout(getContext());
                newLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                newLayout.setOrientation(LinearLayout.HORIZONTAL);

                ImageView iv = new ImageView(getContext());
                iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2.0f));
                iv.setImageResource(R.mipmap.ic_launcher);
                newLayout.addView(iv);

                CheckBox chk = new CheckBox(getContext());
                chk.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                newLayout.addView(chk);


                TextView tv = new TextView(getContext());
                tv.setText(c.getString(1));
                tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2.0f));
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(18);
                newLayout.addView(tv);

                TextView tv2 = new TextView(getContext());
                tv2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2.0f));
                tv2.setText(String.format("%02d",c.getInt(2)) + ":" + String.format("%02d",c.getInt(3)));
                tv2.setTextColor(Color.BLACK);
                tv2.setTextSize(18);
                newLayout.addView(tv2);

                Button b = new Button(getContext());
                b.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));
                b.setText("Delete");
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlarmHelper alarmHelper = new AlarmHelper(getContext());
                        alarmHelper.unschedulePendingIntent((int) v.getTag());
                        MainActivity.dbHelper.deleteArlarm((int) v.getTag());
                        showAlarm(MainActivity.dbHelper.getAlarm());
                    }
                });
                b.setTag(c.getInt(0));
                newLayout.addView(b);
                baseList.addView(newLayout);

            }

        }
    }

}