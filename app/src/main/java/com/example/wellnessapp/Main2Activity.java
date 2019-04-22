package com.example.wellnessapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import com.example.wellnessapp.SleepFragment;

public class Main2Activity extends AppCompatActivity {

    private TextView mTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        mTextMessage = (TextView) findViewById(R.id.message);
        loadFragment(new HomeFragment());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.getMenu().getItem(2).setChecked(true);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment;

            switch (item.getItemId()) {

                case R.id.navigation_sleep:
                    mTextMessage.setText("sleep");
                    fragment = new SleepFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_water:
                    mTextMessage.setText("water");
                    fragment = new WaterFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_home:
                    mTextMessage.setText("Home");
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_step:
                    mTextMessage.setText("step");
                    fragment = new StepFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_study:
                    mTextMessage.setText("study");
                    fragment = new StudyFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };



    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
}
