package com.example.wellnessapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import com.example.wellnessapp.SleepFragment;

public class Main2Activity extends AppCompatActivity implements StepFragment.stepData, StudyFragment.studyData, WaterFragment.waterData, ListActivityData.sleepData {

    public int totalSteps;
    public String studyTime;
    public int waterTotal;
    public String sleepAverage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        loadFragment(new WaterFragment());
        loadFragment(new ListActivityData());
        loadFragment(new StudyFragment());
        loadFragment(new StepFragment());
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
                    fragment = new SleepFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_water:
                    fragment = new WaterFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_step:
                    fragment = new StepFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_study:
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

    //referencing https://stackoverflow.com/questions/9343241/passing-data-between-a-fragment-and-its-container-activity for lines 93-113

    @Override
    public void stepData(int data) {
        totalSteps = data;
    }

    @Override
    public void studyData(String data) {
        studyTime = data;
    }

    @Override
    public void waterData(int data) {
        waterTotal = data;
    }

    @Override
    public void sleepData(String data) {
        sleepAverage = data;
    }

}
