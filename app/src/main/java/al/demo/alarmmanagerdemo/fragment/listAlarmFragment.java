package al.demo.alarmmanagerdemo.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import al.demo.alarmmanagerdemo.R;

/**
 * Created by Utilisateur on 2018-04-01.
 */

public class listAlarmFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle bundle){
        View v = inflater.inflate(R.layout.list_alarm,vg,false);
        return v;
    }

}