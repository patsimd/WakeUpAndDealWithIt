package al.demo.alarmmanagerdemo;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import al.demo.alarmmanagerdemo.fragment.ViewPagerAdapter;
import al.demo.alarmmanagerdemo.view.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {


   // = Calendar.getInstance();

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Home","Events"};
    int Numboftabs =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        pager = (ViewPager) findViewById(R.id.pager);
        adapter =  new ViewPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setViewPager(pager);
    }


}
