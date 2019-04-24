package com.example.wellnessapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class ListActivityData extends Fragment {

    private static final String TAG = "ListActivityData";

    DatabaseHelper mDatabaseHelper;

    private ListView mListView;

    private TextView mTotalSleep;

    public ListActivityData() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_activity_data, container, false);

        onViewCreated(rootView);
        return rootView;
    }

    public void onViewCreated(View rootView) {
        // Inflate the layout for this fragment
        mListView = (ListView) rootView.findViewById(R.id.listView);
        mTotalSleep = (TextView) rootView.findViewById(R.id.tvTotalSleep);
        mDatabaseHelper = new DatabaseHelper(getActivity());

        mTotalSleep.setText(weeklySleepTotal());

        populateListView();

        //toastMessage(weeklySleepTotal());
    }

    //create method
    public void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = mDatabaseHelper.getData();
        ArrayList <String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get the value from the database in column 1, then add it to array list
            listData.add(data.getString(1));
        }

        //create list adapter to adapt items to list view
        ListAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        //set an OnItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String entry = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + entry);
                toastMessage(entry + "onItemClick: You Clicked on " + entry);


//                Intent editScreenIntent = new Intent(ListActivityData.this, EditActivityData.class);
//                editScreenIntent.putExtra("selectedEntry", entry);
//
//                startActivity(editScreenIntent);
//                finish();


                Bundle b = new Bundle();
                b.putString("selectedEntry", entry);
                Fragment fragment = new EditActivityData();
                loadFragment(fragment,b);
//
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                getFragmentManager().beginTransaction().replace(R.id.frame_container, fragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public String weeklySleepTotal(){
        Cursor data = mDatabaseHelper.getWeeklySleep();
        ArrayList <Integer> listData = new ArrayList<>();
        while(data.moveToNext()) {
            String entry = data.getString(1);
            int length = entry.length();
            //get the value from the database in column 1, then add it to array list
            //listData.add(Integer.parseInt(data.getString(1).substring(29, 31)) * 60 + Integer.parseInt(data.getString(1).substring(length - 8, length - 5)));
            String[] row = data.getString(1).split(" ");
            int rowHour = Integer.parseInt(row[4])*60;
            int rowMin = Integer.parseInt(row[6]);
            int dailyMin = rowHour + rowMin;

            listData.add(dailyMin);

        }

        int totalMin = 0;

        for( int i = 0; i < listData.size(); i++){

            totalMin += listData.get(i)/listData.size();
        }

        int finalHours = totalMin/60;
        int finalMin = totalMin%60;

        String weeklyTotal = "\nAverage hours slept this week:" + "\n" + finalHours + " hours and " + finalMin + " minutes\n";

        return weeklyTotal;

    }

    private void loadFragment(Fragment fragment, Bundle b) {
        // load fragment
        fragment.setArguments(b);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }
}
