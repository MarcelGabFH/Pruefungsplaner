package com.example.fhb.pruefungsplaner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.example.fhb.pruefungsplaner.MainActivity.dateneinlesen;
import static com.example.fhb.pruefungsplaner.MainActivity.mAdapter;

public class Favoritenfragment extends Fragment {
    SharedPreferences mSharedPreferences;
    public FragmentTransaction ft;
    public RecyclerView recyclerView;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.terminefragment, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView4);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        List<String> input2 = new ArrayList<>();
        List<String> input3 = new ArrayList<>();
        List<String> input4 = new ArrayList<>();


        //Creating editor to store values to shared preferences
        SharedPreferences mSharedPreferences = v.getContext().getSharedPreferences("json6" , 0);

        //Creating editor to store values to shared preferences
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.apply();


        JSONArray response = new JSONArray();
        String strJson = mSharedPreferences.getString("jsondata","0");
        //second parameter is necessary ie.,Value to return if this preference does not exist.


        if (strJson != null) {
            try {
                response = new JSONArray(strJson);
                for (int i = 0; i < response.length(); i++) {
                    input.add(dateneinlesen.getFach()[Integer.valueOf(response.get(i).toString())] + " " + dateneinlesen.getStudiengang()[Integer.valueOf(response.get(i).toString())]);
                    input2.add(dateneinlesen.getProfname()[Integer.valueOf(response.get(i).toString())] + " " + dateneinlesen.getSemester()[Integer.valueOf(response.get(i).toString())] + " " );
                    input3.add(dateneinlesen.getDatum()[Integer.valueOf(response.get(i).toString())]);
                    input4.add(response.get(i).toString());
                }// define an adapter

                mAdapter = new MyAdapterfavorits(input, input2,input3,input4);
                recyclerView.setAdapter(mAdapter);
            } catch (JSONException e) {
                response = new JSONArray();


            }
        }
        return v;
    }

}
