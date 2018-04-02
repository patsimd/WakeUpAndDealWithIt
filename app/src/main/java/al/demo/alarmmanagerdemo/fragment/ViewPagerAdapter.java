package al.demo.alarmmanagerdemo.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * Created by Utilisateur on 2018-04-02.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {

        if(position == 0)
        {
            listAlarmFragment tab1 = new listAlarmFragment();
            return tab1;
        }
        else
        {
            setAlarmFragment tab2 = new setAlarmFragment();
            return tab2;
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
