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
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

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
    public CalendarView calendar;
    public  Button btnsuche;
    public String date;
    public String month2;
    public String day2;
    public String year2;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.terminefragment, container, false);
         recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView4);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        calendar = (CalendarView) v.findViewById(R.id.caCalender);
        btnsuche = (Button) v.findViewById(R.id.btnDatum);
        calendar.setVisibility(View.GONE);
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
                    String[] date2 = dateneinlesen.getDatum()[Integer.valueOf(response.get(i).toString())].toString().split(" ");
                    input.add(dateneinlesen.getFach()[Integer.valueOf(response.get(i).toString())] + " " + dateneinlesen.getStudiengang()[Integer.valueOf(response.get(i).toString())]);
                    input2.add(dateneinlesen.getProfname()[Integer.valueOf(response.get(i).toString())] +  " " + dateneinlesen.getProfname2()[Integer.valueOf(response.get(i).toString())] + " " + dateneinlesen.getSemester()[Integer.valueOf(response.get(i).toString())]);
                    //input2.add(dateneinlesen.getProfname()[Integer.valueOf(response.get(i).toString())] + " " + dateneinlesen.getSemester()[Integer.valueOf(response.get(i).toString())] + " ");
                    input3.add(dateneinlesen.getDatum()[Integer.valueOf(response.get(i).toString())]);
                    input4.add(response.get(i).toString());
                }// define an adapter
                mAdapter = new MyAdapterfavorits(input, input2,input3,input4);
                recyclerView.setAdapter(mAdapter);
            } catch (JSONException e) {
                response = new JSONArray();
            }
        }

        btnsuche.setOnClickListener(new View.OnClickListener() {
            boolean speicher = false ;
            @Override
            public void onClick(View v) {
                if(speicher){
                    calendar.setVisibility(View.GONE);
                    //calendar.getLayoutParams().height = 0;
                    speicher = false;
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
                                String[] date2 = dateneinlesen.getDatum()[Integer.valueOf(response.get(i).toString())].toString().split(" ");

                                input.add(dateneinlesen.getFach()[Integer.valueOf(response.get(i).toString())] + " " + dateneinlesen.getStudiengang()[Integer.valueOf(response.get(i).toString())]);
                                input2.add(dateneinlesen.getProfname()[Integer.valueOf(response.get(i).toString())] +  " " + dateneinlesen.getProfname2()[Integer.valueOf(response.get(i).toString())] +  " " + dateneinlesen.getSemester()[Integer.valueOf(response.get(i).toString())]);
                                //input2.add(dateneinlesen.getProfname()[Integer.valueOf(response.get(i).toString())] + " " + dateneinlesen.getSemester()[Integer.valueOf(response.get(i).toString())] + " ");
                                input3.add(dateneinlesen.getDatum()[Integer.valueOf(response.get(i).toString())]);
                                input4.add(response.get(i).toString());
                            }// define an adapter
                            mAdapter = new MyAdapterfavorits(input, input2,input3,input4);
                            recyclerView.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            response = new JSONArray();
                        }
                    }
                }else {
                    calendar.setVisibility(View.VISIBLE);
                    speicher = true;
                }
            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
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
                if (month < 10) {
                        month2 = "0" + String.valueOf(month+1);
                    }else
                    {month2 = String.valueOf(month);}

                    if (dayOfMonth < 10) {
                        day2 = "0" + String.valueOf(dayOfMonth);
                    }
                    else
                    {day2 = String.valueOf(dayOfMonth);}
                    year2 = String.valueOf(year);
                    date = year2 +"-"+ month2+"-"+day2;
                if (strJson != null) {
                    try {
                        response = new JSONArray(strJson);
                        for (int i = 0; i < response.length(); i++) {
                            String[] date2 = dateneinlesen.getDatum()[Integer.valueOf(response.get(i).toString())].toString().split(" ");
                            if (date2[0].equals(date)) {
                                input.add(dateneinlesen.getFach()[Integer.valueOf(response.get(i).toString())] + " " + dateneinlesen.getStudiengang()[Integer.valueOf(response.get(i).toString())]);
                                input2.add(dateneinlesen.getProfname()[Integer.valueOf(response.get(i).toString())] +  " " + dateneinlesen.getProfname2()[Integer.valueOf(response.get(i).toString())] +  " " + dateneinlesen.getSemester()[Integer.valueOf(response.get(i).toString())]);
                                input3.add(dateneinlesen.getDatum()[Integer.valueOf(response.get(i).toString())]);
                                input4.add(response.get(i).toString());
                            }
                        }// define an adapter
                        mAdapter = new MyAdapterfavorits(input, input2,input3,input4);
                        recyclerView.setAdapter(mAdapter);
                    } catch (JSONException e) {
                        response = new JSONArray();
                    }
                }
            }
        });
        return v;
    }
}
