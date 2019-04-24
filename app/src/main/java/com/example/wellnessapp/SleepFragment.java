package com.example.wellnessapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import static android.content.Context.MODE_PRIVATE;

public class SleepFragment extends Fragment {

    private EditText startH;
    private EditText startM;
    private EditText endH;
    private EditText endM;
    private Button analyse;
    private TextView amount;
    private ImageView moon;
    private ImageView sun;


    private static final String TAG = "MainActivity";
    //database
    DatabaseHelper mDatabaseHelper;
    private Button btnDiary;
    private Button btnAdd;
    public String currentDate = new SimpleDateFormat("dd/MM/yyyy_HHmmss").format(Calendar.getInstance().getTime()).substring(0,10);

    public SleepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sleep, container, false);

        onViewCreated(rootView);
        return rootView;
    }

    public void onViewCreated(View rootView) {

        startH = (EditText) rootView.findViewById(R.id.startTimeHour);
        endH = (EditText) rootView.findViewById(R.id.endTimeHour);
        startM = (EditText) rootView.findViewById(R.id.startTimeMin);
        endM = (EditText) rootView.findViewById(R.id.endTimeMin);
        analyse = (Button) rootView.findViewById(R.id.calculate);
        amount = (TextView) rootView.findViewById(R.id.result);
        moon = (ImageView) rootView.findViewById(R.id.imageMoon);
        sun = (ImageView) rootView.findViewById(R.id.imageSun);

        //oncreate for the database
        btnDiary = (Button) rootView.findViewById(R.id.bDiary);
        btnAdd = (Button) rootView.findViewById(R.id.bAdd);
        mDatabaseHelper = new DatabaseHelper( getActivity());

        analyse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //convert string of textView to number int
                InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                int startingH = Integer.parseInt(startH.getText().toString());
                int endingH = Integer.parseInt(endH.getText().toString());
                int startingM = Integer.parseInt(startM.getText().toString());
                int endingM = Integer.parseInt(endM.getText().toString());


                //result stored in subtract variable: calculate hours then minutes

                //If sleeping before 12am: sleep time - wake time + 12
                int subtractH = endingH - startingH +12;
                //If sleeping after 12am: wake time - sleep time
                if (endingH > startingH == true) {
                    subtractH = endingH - startingH;
                }


                //If waking minutes is larger than sleeping minutes
                int subtractM = endingM - startingM;
                //If waking minutes is less than sleeping minutes:sleep - wake + 60 and then minus one hour
                if (endingM < startingM == true) {
                    subtractM = endingM - startingM + 60;
                    subtractH = subtractH - 1;
                }

                amount.setText("Date: " + currentDate + "\n" + " Time Slept: " + String.valueOf(subtractH) + " hrs " + String.valueOf(subtractM) + " mins");
                btnAdd.setVisibility(View.VISIBLE);
                analyse.setVisibility(View.INVISIBLE);
                moon.setVisibility(View.VISIBLE);
                sun.setVisibility(View.INVISIBLE);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newEntry = amount.getText().toString();

                if (amount.length() != 0) {
                    AddData(newEntry);
                    amount.setText("");
                } else {
                    toastMessage("You must put something in the field");
                }
            }
        });

        btnDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ListActivityData());
            }
        });
    }

    public void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addData(newEntry);
        if (insertData) {
            toastMessage("Inserted in diary!");
        } else {
            toastMessage("Please insert data!");
        }
        btnAdd.setVisibility(View.INVISIBLE);
        analyse.setVisibility(View.VISIBLE);
        moon.setVisibility(View.INVISIBLE);
        sun.setVisibility(View.VISIBLE);
        startH.getText().clear();
        endH.getText().clear();
        startM.getText().clear();
        endM.getText().clear();
    }

    private void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
