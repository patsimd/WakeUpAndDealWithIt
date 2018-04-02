package al.demo.alarmmanagerdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import al.demo.alarmmanagerdemo.fragment.ViewPagerAdapter;
import al.demo.alarmmanagerdemo.fragment.listAlarmFragment;
import al.demo.alarmmanagerdemo.view.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {


   // = Calendar.getInstance();

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Home","Events"};
    int Numboftabs =2;
    public static DatabaseHelper dbHelper;
    public static int frag1;
    public static int frag2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper =  new DatabaseHelper(this);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        pager = (ViewPager) findViewById(R.id.pager);
        adapter =  new ViewPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setViewPager(pager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //if(position == 0) {
                    Fragment c = adapter.getItem(0);
                    ((listAlarmFragment) (c)).showAlarm(dbHelper.getAlarm());
               // }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //TODO


            }
        });


    }


}
