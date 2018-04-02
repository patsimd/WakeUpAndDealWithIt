package al.demo.alarmmanagerdemo.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import al.demo.alarmmanagerdemo.MainActivity;

/**
 * Created by Utilisateur on 2018-04-02.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {


    protected static Fragment first;
    protected static Fragment second;
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {

        if(position == 0)
        {
            if(first == null)
                 first = new listAlarmFragment();
          //  MainActivity.frag1 = tab1.getId();

            return first;
        }
        else
        {
            if(second == null)
                second = new setAlarmFragment();
            //MainActivity.frag2 = tab2.getId();
            return second;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Alarm list";
            case 1: return "Set Alarm";
        }
        return "";
    }

}
