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
import android.widget.ImageView;
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
            getImage(mWaterCount);
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
                getImage(mWaterCount);
                SharedPreferences.Editor editor = wcSharedPref.edit();
                editor.remove("com.example.waterCounter.wcSharedPref");
                editor.putInt("com.example.waterCounter.wcSharedPref", mWaterCount);
                editor.apply();
            }
        });


    }

    public void getImage (int mWaterCount) {
        switch (mWaterCount) {

            case 0:

                ImageView leftProgress0 = (ImageView) getView().findViewById(R.id.leftWater0);
                ImageView rightProgress0 = (ImageView) getView().findViewById(R.id.rightWater0);
                leftProgress0.setVisibility(View.VISIBLE);
                rightProgress0.setVisibility(View.VISIBLE);

                break;

            case 1:

                ImageView leftProgress1 = (ImageView) getView().findViewById(R.id.leftWater1);
                ImageView rightProgress1 = (ImageView) getView().findViewById(R.id.rightWater1);
                leftProgress1.setVisibility(View.VISIBLE);
                rightProgress1.setVisibility(View.VISIBLE);

                break;

            case 2:

                ImageView leftProgress2 = (ImageView) getView().findViewById(R.id.leftWater2);
                ImageView rightProgress2 = (ImageView) getView().findViewById(R.id.rightWater2);
                leftProgress2.setVisibility(View.VISIBLE);
                rightProgress2.setVisibility(View.VISIBLE);

                break;

            case 3:

                ImageView leftProgress3 = (ImageView) getView().findViewById(R.id.leftWater3);
                ImageView rightProgress3 = (ImageView) getView().findViewById(R.id.rightWater3);
                leftProgress3.setVisibility(View.VISIBLE);
                rightProgress3.setVisibility(View.VISIBLE);

                break;

            case 4:

                ImageView leftProgress4 = (ImageView) getView().findViewById(R.id.leftWater4);
                ImageView rightProgress4 = (ImageView) getView().findViewById(R.id.rightWater4);
                leftProgress4.setVisibility(View.VISIBLE);
                rightProgress4.setVisibility(View.VISIBLE);

                break;

            case 5:

                ImageView leftProgress5 = (ImageView) getView().findViewById(R.id.leftWater5);
                ImageView rightProgress5 = (ImageView) getView().findViewById(R.id.rightWater5);
                leftProgress5.setVisibility(View.VISIBLE);
                rightProgress5.setVisibility(View.VISIBLE);

                break;

            case 6:

                ImageView leftProgress6 = (ImageView) getView().findViewById(R.id.leftWater6);
                ImageView rightProgress6 = (ImageView) getView().findViewById(R.id.rightWater6);
                leftProgress6.setVisibility(View.VISIBLE);
                rightProgress6.setVisibility(View.VISIBLE);

                break;

            case 7:

                ImageView leftProgress7 = (ImageView) getView().findViewById(R.id.leftWater7);
                ImageView rightProgress7 = (ImageView) getView().findViewById(R.id.rightWater7);
                leftProgress7.setVisibility(View.VISIBLE);
                rightProgress7.setVisibility(View.VISIBLE);

                break;

            case 8:

                ImageView leftProgress8 = (ImageView) getView().findViewById(R.id.leftWater8);
                ImageView rightProgress8 = (ImageView) getView().findViewById(R.id.rightWater8);
                leftProgress8.setVisibility(View.VISIBLE);
                rightProgress8.setVisibility(View.VISIBLE);

                break;


            default:

                ImageView leftProgress = (ImageView) getView().findViewById(R.id.leftWater8);
                ImageView rightProgress = (ImageView) getView().findViewById(R.id.rightWater8);
                leftProgress.setVisibility(View.VISIBLE);
                rightProgress.setVisibility(View.VISIBLE);


        }
    }
}
