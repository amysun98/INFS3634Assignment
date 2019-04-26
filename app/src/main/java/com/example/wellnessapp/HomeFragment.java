package com.example.wellnessapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class HomeFragment extends Fragment {

    public TextView mAverageSleep;
    public TextView mWaterTotal;
    public TextView mStepTotal;
    public TextView mStudyTotal;
    public TextView mAdvice;
    String adviceText = "";
    public Main2Activity activityMain;



    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        onViewCreated(rootView);
        return rootView;
    }

    public void onViewCreated (View rootView) {

        activityMain = (Main2Activity) getActivity();
        getAdvice(rootView);

        mAverageSleep = (TextView) rootView.findViewById(R.id.tvAverageSleep);
        mAverageSleep.setText(activityMain.sleepAverage);

        mWaterTotal = (TextView) rootView.findViewById(R.id.tvWaterTotal);
        if (activityMain.waterTotal < 8) {

            int waterRequired = 8 - activityMain.waterTotal;
            mWaterTotal.setText("\nYou need to drink " + waterRequired + " more cups of water!\n");
        } else {
            mWaterTotal.setText("\nYou're well hydrated today!\n");
        }

        mStepTotal = (TextView) rootView.findViewById(R.id.tvTotalSteps);
        if (activityMain.totalSteps < 10000) {
            mStepTotal.setText("\nYou've taken " + activityMain.totalSteps + "/10000 steps today\n");
        }
        else {
        mStepTotal.setText("\nYou've hit your daily step target!\n");
        }

        mStudyTotal = (TextView) rootView.findViewById(R.id.tvTotalStudy);
        mStudyTotal.setText("\n"+ activityMain.studyTime + "\n");



    }

        public String getAdvice (final View rootView) {


            RequestQueue requestQueue;
            final String url = "https://api.adviceslip.com/advice";

            requestQueue = Volley.newRequestQueue(getActivity());

            JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject obj = response.getJSONObject("slip");
                        String advice = obj.getString("advice");
                        mAdvice = (TextView) rootView.findViewById(R.id.tvAdvice);
                        adviceText = advice;
                        mAdvice.setText("DailyAdvice: "+ adviceText);
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }
            },

                    new Response.ErrorListener() {
                        @Override
                        // Handles errors that occur due to Volley
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley", "Error");
                        }
                    }
            );
            requestQueue.add(obreq);
            return adviceText;

        }
    }



