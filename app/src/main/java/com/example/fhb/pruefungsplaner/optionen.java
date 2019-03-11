package com.example.fhb.pruefungsplaner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import static com.example.fhb.pruefungsplaner.MainActivity.dateneinlesen;
import static com.example.fhb.pruefungsplaner.MainActivity.mAdapter;

public class optionen extends Fragment {
    public Intent calIntent;
    public boolean speicher;
    public SharedPreferences.Editor mEditor;
    public JSONArray response;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.optionfragment, container, false);

        Switch SWgooglecalender = (Switch) v.findViewById(R.id.switch2);


        //holder.zahl1 = position;
        SharedPreferences mSharedPreferences = v.getContext().getSharedPreferences("json8",0);
        //Creating editor to store values to shared preferences
        mEditor = mSharedPreferences.edit();

        response = new JSONArray();
        String strJson = mSharedPreferences.getString("jsondata2","0");
        //second parameter is necessary ie.,Value to return if this preference does not exist.
        if (strJson != null) {
            try {
                response = new JSONArray(strJson);
            } catch (JSONException e) {

            }
        }

        int i;
        speicher = false;
        for (i = 0;i < response.length();i++) {
            { try {
                if (response.get(i).toString().equals("1")) {
                    SWgooglecalender.setChecked(true);
                    speicher = true;
                }
            } catch (JSONException e) {

            }}}
        if (!speicher){


        }




       SWgooglecalender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked)
                {
                    mEditor.clear();
                    mEditor.apply();
                    response.put("1");
                    mEditor.putString("jsondata2", response.toString());
                    mEditor.apply();
                    Toast.makeText(v.getContext(),"Prüfungen werden jetzt zum Kalender hinzugefügt", Toast.LENGTH_SHORT).show();
                }

                if(!isChecked)
                {
                    mEditor.clear().apply();
                    mEditor.remove("jsondata2").apply();
                }

            }
        });

        return v;
    }


    public void insertfav()
    {


    }


}

