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
import android.widget.Button;
import android.widget.Toast;


public class EditActivityData extends Fragment {


    private static final String TAG  = "Edit Data Activity";

    private Button btnDelete;

    DatabaseHelper mDatabaseHelper;

    private String selectedEntry;

    public ListActivityData mListActivityData;


    public EditActivityData() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = this.getArguments();
        if(b != null){
            selectedEntry =b.getString("selectedEntry");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_activity_data, container, false);

        onViewCreated(rootView);
        return rootView;
    }

    public void onViewCreated(View rootView){

        btnDelete = (Button) rootView.findViewById(R.id.btnDelete);
        mDatabaseHelper = new DatabaseHelper(getActivity());


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage("removed from database"+ " " + selectedEntry);
                mDatabaseHelper.deleteData(selectedEntry);
                loadFragment(new ListActivityData());

            }
        });
    }


    private void toastMessage(String message){
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



