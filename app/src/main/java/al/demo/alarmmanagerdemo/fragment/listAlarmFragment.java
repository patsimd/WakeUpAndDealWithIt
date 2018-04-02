package al.demo.alarmmanagerdemo.fragment;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
            newLayout.setOrientation(LinearLayout.HORIZONTAL);
            TextView tv = new TextView(getContext());
            tv.setText(c.getString(1));
            newLayout.addView(tv);
            baseList.addView(newLayout);

        }
        return v;
    }

}