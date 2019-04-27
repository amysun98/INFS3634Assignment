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

    waterData passWaterData;

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

        //get SharedPreference and default values for date amd total count

        SharedPreferences dateSharedPref = getActivity().getSharedPreferences("com.example.waterCounter.dateSharedPref", MODE_PRIVATE);

        SharedPreferences wcSharedPref = getActivity().getSharedPreferences("com.example.waterCounter.wcSharedPref", MODE_PRIVATE);


        mCounterDate = dateSharedPref.getString("com.example.waterCounter.dateSharedPref", "2019/04/13");
        mWaterCount = wcSharedPref.getInt("com.example.WaterCounter.wcSharedPref", 6);

        //if the fragment is accessed on the same day, it will restore count.
        // If the date does not match (i.e. fragment accessed the next day) it will reset total count to 0

        if (mCounterDate.equals(currentTime)) {
            mWaterCount = wcSharedPref.getInt("com.example.waterCounter.wcSharedPref", 6);
            mWaterTotal = rootView.findViewById(R.id.tvWaterTotal);
            mWaterTotal.setText("You've had " + mWaterCount + " cups of water today!");
            SharedPreferences.Editor editor = dateSharedPref.edit();
            editor.remove("com.example.waterCounter.dateSharedPref");
            editor.putString("com.example.waterCounter.dateSharedPref", currentTime);
            editor.apply();
            passWaterData.waterData(mWaterCount);
            getImage(mWaterCount, rootView);

        } else {
            mCounterDate = currentTime;
            mWaterCount = 0;
            mWaterTotal = rootView.findViewById(R.id.tvWaterTotal);
            mWaterTotal.setText("You've had " + mWaterCount + " cups of water today!");
            SharedPreferences.Editor editor = dateSharedPref.edit();
            editor.remove("com.example.waterCounter.dateSharedPref");
            editor.putString("com.example.waterCounter.dateSharedPref", currentTime);
            editor.apply();
            passWaterData.waterData(mWaterCount);
            getImage(mWaterCount, rootView);

        }

        //increase count when button is pressed

        fillButton = (Button) rootView.findViewById(R.id.fillButton);
        fillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences wcSharedPref = getActivity().getSharedPreferences("com.example.waterCounter.wcSharedPref", MODE_PRIVATE);


                mWaterCount++;
                mWaterTotal.setText("You've had " + mWaterCount + " cups of water today!");
                getImage(mWaterCount, v);
                SharedPreferences.Editor editor = wcSharedPref.edit();
                editor.remove("com.example.waterCounter.wcSharedPref");
                editor.putInt("com.example.waterCounter.wcSharedPref", mWaterCount);
                editor.apply();
                passWaterData.waterData(mWaterCount); }
        });


    }

    //display the relevant side progress bar image depending on water count
    public void getImage (int mWaterCount, View rootView) {
        switch (mWaterCount) {

            case 0:

                ImageView rightProgress0 = (ImageView) rootView.findViewById(R.id.rightWater0);
                rightProgress0.setVisibility(View.VISIBLE);

                break;

            case 1:

                ImageView rightProgress1 = (ImageView) getActivity().findViewById(R.id.rightWater1);
                rightProgress1.setVisibility(View.VISIBLE);

                break;

            case 2:

                ImageView rightProgress2 = (ImageView) rootView.findViewById(R.id.rightWater2);
                rightProgress2.setVisibility(View.VISIBLE);

                break;

            case 3:

                ImageView rightProgress3 = (ImageView) rootView.findViewById(R.id.rightWater3);
                rightProgress3.setVisibility(View.VISIBLE);

                break;

            case 4:

                ImageView rightProgress4 = (ImageView) rootView.findViewById(R.id.rightWater4);
                rightProgress4.setVisibility(View.VISIBLE);

                break;

            case 5:

                ImageView rightProgress5 = (ImageView) rootView.findViewById(R.id.rightWater5);
                rightProgress5.setVisibility(View.VISIBLE);

                break;

            case 6:

                ImageView rightProgress6 = (ImageView) rootView.findViewById(R.id.rightWater6);
                rightProgress6.setVisibility(View.VISIBLE);

                break;

            case 7:

                ImageView rightProgress7 = (ImageView) rootView.findViewById(R.id.rightWater7);
                rightProgress7.setVisibility(View.VISIBLE);

                break;

            case 8:

                ImageView rightProgress8 = (ImageView) rootView.findViewById(R.id.rightWater8);
                rightProgress8.setVisibility(View.VISIBLE);

                break;


            default:

                ImageView rightProgress = (ImageView) rootView.findViewById(R.id.rightWater8);
                rightProgress.setVisibility(View.VISIBLE);


        }
    }

    // referencing https://stackoverflow.com/questions/9343241/passing-data-between-a-fragment-and-its-container-activity for lines 183 to 195

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        passWaterData = (waterData) context;
    }

    public void onPassWaterData (int water){
        passWaterData.waterData(water);
    }

    public interface waterData {
        public void waterData (int water);
    }
}
