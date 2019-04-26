package com.example.wellnessapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.MODE_PRIVATE;



public class StudyFragment extends Fragment {

    private EditText mInput;
    private Button mButtonSet;


    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private TextView mTotalTime;
    private CountDownTimer mCountDownTimer;
    private ProgressBar mProgressBar;

    //Boolean will indicate if the Timer is running or not.
    private boolean mTimerRunning;

    //Note: 1 second = 1000 milliseconds
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;
    public String input;
    int intInput;

    public String sysTime = new SimpleDateFormat("yyyy/MM/dd_HHmmss").format(Calendar.getInstance().getTime());
    public String currentTime = sysTime.substring(0, 10);
    public String mCounterDate = currentTime;

    public int total = 1;

    studyData passStudyData;

    //Reference 1:
    //Coding in Flow, 2019, "Countdown Timer", Accessed 12th April 2019, https://codinginflow.com/tutorials/android/countdowntimer/part-4-time-input.

    //Reference 2:
    //MaterialUI, 2019, "Material Design Colours", Accessed 18th April 2019, https://www.materialui.co/colors

    public StudyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_study, container, false);

        onViewCreated(rootView);
        return rootView;
    }


    public void onViewCreated(View rootView) {

        //Reference 3:
        //Android Developers, 2019, "Save key-value data", Accessed 16th April 2019, https://developer.android.com/training/data-storage/shared-preferences.
        //Shared preference have been used to store a small number of variables, such as the the date and the total time studied as seen here

        SharedPreferences timerDateSharedPref = getActivity().getSharedPreferences("com.example.studytimer1.dateSharedPref", MODE_PRIVATE);
        SharedPreferences totalMinutesSharedPref = getActivity().getSharedPreferences("com.example.studytimer1.totalMinutesSharedPref", MODE_PRIVATE);

        mCounterDate = timerDateSharedPref.getString("com.example.studytimer1.dateSharedPref", "2019/04/18");
        total = totalMinutesSharedPref.getInt("com.example.studytimer1.totalMinutesSharedPref", 0);

        if (mCounterDate.equals(currentTime)) {
            total = totalMinutesSharedPref.getInt("com.example.studytimer1.totalMinutesSharedPref", 0);
            mTotalTime = rootView.findViewById(R.id.total_time);
            mTotalTime.setText("You have studied " + total + " minutes today!"); //NEW
            SharedPreferences.Editor editor = timerDateSharedPref.edit();
            editor.remove("com.example.studytimer1.dataSharedPref");
            editor.putString("com.example.studytimer1.dateSharedPref", currentTime);
            editor.apply();
            passStudyData.studyData(mTotalTime.getText().toString());

        } else {
            mCounterDate = currentTime;
            total = 0;
            mTotalTime = rootView.findViewById(R.id.total_time);
            mTotalTime.setText("You have studied " + total + " minutes today!");
            SharedPreferences.Editor editor = timerDateSharedPref.edit();
            editor.remove("come.example.studytimer1.dateSharedPref");
            editor.putString("com.example.studytimer1.dateSharedPref", currentTime);
            editor.apply();
            passStudyData.studyData(mTotalTime.getText().toString());
        }

        mInput = rootView.findViewById(R.id.edit_text_input); //fix this
        mButtonSet = rootView.findViewById(R.id.button_set);
        mTextViewCountDown = rootView.findViewById(R.id.text_view_countdown);
        mButtonStartPause = rootView.findViewById(R.id.button_start_pause);
        mTotalTime = rootView.findViewById(R.id.total_time);
        mProgressBar = rootView.findViewById(R.id.progressBar);

        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //First retrieve user input,
                input = mInput.getText().toString();
                //then store user input as a integer. This will allow us to perform mathematical operations with user input
                intInput = Integer.parseInt(mInput.getText().toString());

                //If the length of the input as a String is equal to 0, then a toast message to enter a number will appear
                //If 0 is entered, then a toast message to enter a positive number appears
                if (input.length() == 0) {
                    Toast.makeText(getActivity(), "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText( getActivity(), "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }
                setTime(millisInput);
                mInput.setText("");

            }
        });

        //If the timer is running, the timer will stop when the Start Button is clicked
        //If timer is NOT running, the timer is resumed again when this button is clicked
        //It will also change from Button Text from Displaying "Start" to --> "Pause" (Later sections will cover this)
        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
    }

//--Other METHODS after onCreate Method--

    //This method allows users to set the Timer with their input
    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        //CountDownTimer as an object is created and the onTick method will be called every second, hence 10000 as a parameter
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;

                //As a result, onTick will update the CountDownText to display relevant seconds left in the Timer
                updateCountDownText();
                //The Progress Bar will then only be visible when the Start Button is pressed
                mProgressBar.setVisibility(View.VISIBLE);

            }

            //This method corresponds to when the Timer hits 00:00, where a toast message will also remind user to take breaks
            @Override
            public void onFinish() {
                mTimerRunning = false;
                Toast.makeText(getActivity(), "Make sure to take adequate breaks!", Toast.LENGTH_LONG).show();
                updateWatchInterface();

                SharedPreferences totalMinutesSharedPref = getActivity().getSharedPreferences("com.example.studytimer1.totalMinutesSharedPref", MODE_PRIVATE);

                //Here, the number of minutes is only added to total amount studied for that day, only when the Timer hits 00:00
                total += intInput;

                SharedPreferences.Editor editor = totalMinutesSharedPref.edit();
                editor.remove("com.example.studytimer1.totalMinutesSharedPref");
                editor.putInt("com.example.studytimer1.totalMinutesSharedPref", total);
                editor.apply();

                //Here, the old total time is removed, new value is then added, and then total new value is displayed into the TextView
                mTotalTime.setText(String.valueOf("You have studied " + totalMinutesSharedPref.getInt("com.example.studytimer1.totalMinutesSharedPref", 0)
                        + " minutes today!"));
                passStudyData.studyData(mTotalTime.getText().toString());

            }
        }.start();

        mTimerRunning = true;
        updateWatchInterface();

    }


    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }

    //When resetTimer is called, CountDownTimer resets to default starting time
    //We must also update the Count Down Text and the Interface by calling respective methods
    //(See later code below for updateWatchInterface's explanation)

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }
    //This method updates the Count Down Text EVERY second
    private void updateCountDownText() {

        //It is necessary to change the milliseconds back to seconds, minutes etc... by /1000
        //The modulus returns what is left from dividing to get hours, which is then equal to the amount of minutes left
        //This is also the same for when calculating the amount of seconds left

        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        //This is a wew string to convert hours, minutes, and seconds into time string
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        //Now, set the the TextView for Count Down Timer to the time left (String)
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    //If timer is running, then EditText and Set Button is made INVISIBLE.
    //Progress Bar is made VISIBLE as an additional UI element and when the time starts, the Start button's text
    //will be changed to display "Pause" to allow users to click to stop the timer
    //This is the opposite for the if else statement

    private void updateWatchInterface() {
        if (mTimerRunning) {
            mInput.setVisibility(View.INVISIBLE);
            mButtonSet.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
            mButtonStartPause.setText("Pause");
        } else {
            mInput.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Start");

            if (mTimeLeftInMillis < 1000) {
                mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);
            }

            if (mTimeLeftInMillis < mStartTimeInMillis) {

            }
        }
    }

    //Necessary for users to input using their android phones
    private void closeKeyboard() {
        View view = this.getView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    //This will save variables such as seconds left, the state of the Timer when user leaves the App
    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences prefs = getActivity().getSharedPreferences("com.example.studytimer1.prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    //This will reset timer to default values when the App is is started
    //Default values starting time as as 0 minutes, time left as 10 minutes, and state of timer as NOT running

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences prefs = getActivity().getSharedPreferences("com.example.studytimer1prefs", MODE_PRIVATE);

        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateWatchInterface();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            //This helps to make sure that the time left in seconds is positive, and if not,
            //the timer will set the time left to 00:00, the state of timer running to be false and also updating UI respectively

            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }
    }

    //referencing https://stackoverflow.com/questions/9343241/passing-data-between-a-fragment-and-its-container-activity for lines 353-364

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        passStudyData = (studyData) context;
    }

    public void onPassStudyData (String study){
        passStudyData.studyData(study);
    }

    public interface studyData {
        public void studyData (String study);
    }
}


