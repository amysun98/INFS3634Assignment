package com.example.wellnessapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class WaterFragment extends Fragment {

    public int mWaterCount = 3;
    public TextView mWaterTotal;
    public int mWaterInput;
    public String time = new SimpleDateFormat("yyyy/MM/dd_HHmmss").format(Calendar.getInstance().getTime());
    public String currentTime = time.substring(0, 10);
    public Button fillButton;
    public String mCounterDate = currentTime;

    public WaterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_water, container, false);

        onViewCreated(rootView);
        return rootView;
    }

    public void onViewCreated(View rootView) {


        SharedPreferences dateSharedPref = getActivity().getSharedPreferences("com.example.waterCounter.dateSharedPref", MODE_PRIVATE);

        SharedPreferences wcSharedPref = getActivity().getSharedPreferences("com.example.waterCounter.wcSharedPref", MODE_PRIVATE);


        mCounterDate = dateSharedPref.getString("com.example.waterCounter.dateSharedPref", "2019/04/13");
        mWaterCount = wcSharedPref.getInt("com.example.WaterCounter.wcSharedPref", 6);

        if (mCounterDate.equals(currentTime)) {
            mWaterCount = wcSharedPref.getInt("com.example.waterCounter.wcSharedPref", 6);
            mWaterTotal = rootView.findViewById(R.id.tvWaterTotal);
            mWaterTotal.setText("You've had " + mWaterCount + " cups of water today!");
            SharedPreferences.Editor editor = dateSharedPref.edit();
            editor.remove("com.example.waterCounter.dateSharedPref");
            editor.putString("com.example.waterCounter.dateSharedPref", currentTime);
            editor.apply();
        } else {
            mCounterDate = currentTime;
            mWaterCount = 0;
            mWaterTotal = rootView.findViewById(R.id.tvWaterTotal);
            mWaterTotal.setText("You've had " + mWaterCount + " cups of water today!");

            SharedPreferences.Editor editor = dateSharedPref.edit();
            editor.remove("com.example.waterCounter.dateSharedPref");
            editor.putString("com.example.waterCounter.dateSharedPref", currentTime);
            editor.apply();
        }

        fillButton = (Button) rootView.findViewById(R.id.fillButton);
        fillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences wcSharedPref = getActivity().getSharedPreferences("com.example.waterCounter.wcSharedPref", MODE_PRIVATE);


                mWaterCount++;
                mWaterTotal.setText("You've had " + mWaterCount + " cups of water today!");
                SharedPreferences.Editor editor = wcSharedPref.edit();
                editor.remove("com.example.waterCounter.wcSharedPref");
                editor.putInt("com.example.waterCounter.wcSharedPref", mWaterCount);
                editor.apply();
            }
        });


    }

}
