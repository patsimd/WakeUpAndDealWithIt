package al.demo.alarmmanagerdemo.fragment;

import android.database.Cursor;
import android.graphics.Color;
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

import al.demo.alarmmanagerdemo.DatabaseHelper;
import al.demo.alarmmanagerdemo.MainActivity;
import al.demo.alarmmanagerdemo.R;

/**
 * Created by Utilisateur on 2018-04-01.
 */

public class listAlarmFragment extends Fragment{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle bundle){
        View v = inflater.inflate(R.layout.list_alarm,vg,false);
        Cursor c = MainActivity.dbHelper.getAlarm();

        LinearLayout baseList = (LinearLayout)v.findViewById(R.id.baseList);
        while(c.moveToNext()){
            Log.i("ReadAlarm",String.valueOf(c.getInt(0)));
            LinearLayout newLayout = new LinearLayout(getContext());
            newLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
            newLayout.setOrientation(LinearLayout.HORIZONTAL);

            ImageView iv = new ImageView(getContext());
            iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,2.0f));
            iv.setImageResource(R.mipmap.ic_launcher);
            newLayout.addView(iv);

            CheckBox chk = new CheckBox(getContext());
            chk.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
            newLayout.addView(chk);


            TextView tv = new TextView(getContext());
            tv.setText(c.getString(1));
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,2.0f));
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(18);
            newLayout.addView(tv);

            TextView tv2 = new TextView(getContext());
            tv2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,2.0f));
            tv2.setText(String.valueOf(c.getInt(2)) + ":" + String.valueOf(c.getInt(3)));
            tv2.setTextColor(Color.BLACK);
            tv2.setTextSize(18);
            newLayout.addView(tv2);

            Button b = new Button(getContext());
            b.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,0.1f));
            b.setText("Delete");
            newLayout.addView(b);
            baseList.addView(newLayout);

        }
        return v;
    }

}