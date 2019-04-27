package com.example.wellnessapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class StepFragment extends Fragment implements SensorEventListener {

    TextView tv_steps;
    SensorManager sensorManager; // SensorManager allows to access the device's sensors.
    boolean running = false; // if false, Sensor stops running. if true, Sensor is running.
    stepData passStepData;

    public StepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);

        onViewCreated(rootView);
        return rootView;
    }

    public void onViewCreated(View rootView) {
        tv_steps = (TextView) rootView.findViewById(R.id.tv_steps);

        // SensorManager lets you access the device's sensors.
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

    }
    @Override
    public void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER); // TYPE_STEP_COUNTER a constant describing a step count sensor
        // if step count sensor is found this will be executed.
        // SENSOR_DELAY_UI delays the speed at which sensor events are received.
        if(countSensor != null){
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            // If step count sensor is not found, this will appear.
            Toast.makeText(getContext(), "Sensor not found!", Toast.LENGTH_SHORT).show();
        }

    }

    // onSensorChanged method is called whenever the sensor reports a new value.
    @Override
    public void onSensorChanged(SensorEvent event) {
        running = true;
        if (running) {
            try {
                int number = Integer.parseInt(((TextView) getView().findViewById(R.id.tv_steps)).getText().toString());
                passStepData.stepData(number);
                // if the number of steps walked is not equal to 10,000 the number of steps walked will be displayed
                if (number != 10000) {
                    tv_steps.setText(String.valueOf(event.values[0]));
                } else {
                    // if steps walked is equal to 10,000 then this message will appear.
                    Toast msg = Toast.makeText(getContext(), "Congratulations, you have reached the daily goal!",
                            Toast.LENGTH_LONG);
                    msg.show();
                }
            } catch (Exception ex) {
            }
        }

    }

    // onPause method is called when the Activity leaves the foreground.
    @Override
    public void onPause(){
        super.onPause();
        // Sensor continues running on pause
        running = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // referencing https://stackoverflow.com/questions/9343241/passing-data-between-a-fragment-and-its-container-activity for lines 90 to 106

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        passStepData = (stepData) context;
        System.err.print("On attach is working");
    }

    public void onPassStepData (int steps){
        passStepData.stepData(steps);
    }

    public interface stepData {
        public void stepData(int steps);
    }

}
