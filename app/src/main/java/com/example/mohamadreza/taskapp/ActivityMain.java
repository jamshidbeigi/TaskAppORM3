package com.example.mohamadreza.taskapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mohamadreza.taskapp.models.CurrentPosition;
import com.example.mohamadreza.taskapp.models.TaskLab;
import com.example.mohamadreza.taskapp.models.UserLab;

public class ActivityMain extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ActivityMain.class);
        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Long guestId= (long) -1;
        if(CurrentPosition.getUserId().equals(guestId)) {
            TaskLab.getInstance().deleteAll(guestId);
            UserLab.getInstance().deleteUser(guestId);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.task_view_pager);

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return FragmentTasksList.newInstance(position);
            }

            @Override
            public int getCount() {
                return mTabLayout.getTabCount();
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
    }
}

