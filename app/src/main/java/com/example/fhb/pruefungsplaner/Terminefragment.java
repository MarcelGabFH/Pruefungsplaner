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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.example.fhb.pruefungsplaner.MainActivity.dateneinlesen;
import static com.example.fhb.pruefungsplaner.MainActivity.mAdapter;

public class Terminefragment extends Fragment {
    SharedPreferences mSharedPreferences;
    public FragmentTransaction ft;
    public RecyclerView recyclerView;
    public CalendarView calendar;
    public Button btnsuche;
    public String date;
    public String month2;
    public String day2;
    public String year2;
    List<String> WerteZumAnzeigen;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.terminefragment, container, false);
        //setContentView(R.layout.hauptfenster);
        WerteZumAnzeigen = dateneinlesen.getab();
        //hinzufügen von recycleview
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView4);
        recyclerView.setVisibility(View.VISIBLE);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        mSharedPreferences = v.getContext().getSharedPreferences("json6" , 0);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        List<String> input2 = new ArrayList<>();
        List<String> input3 = new ArrayList<>();
        List<String> input4 = new ArrayList<>();
        //gr
        for (int i = 0; i < WerteZumAnzeigen.size(); i++) {
            input.add(dateneinlesen.getFach()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " + dateneinlesen.getStudiengang()[Integer.valueOf(WerteZumAnzeigen.get(i))]);
            input2.add(dateneinlesen.getProfname()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " +dateneinlesen.getProfname2()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " + dateneinlesen.getSemester()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " );
            input3.add(dateneinlesen.getDatum()[Integer.valueOf(WerteZumAnzeigen.get(i))]);
            input4.add(WerteZumAnzeigen.get(i));

        }// define an adapter

        mAdapter = new MyAdapter(input, input2,input3,input4);

        recyclerView.setAdapter(mAdapter);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView4);
        recyclerView.setVisibility(View.VISIBLE);
        calendar = (CalendarView) v.findViewById(R.id.caCalender);
        btnsuche = (Button) v.findViewById(R.id.btnDatum);

        calendar.setVisibility(View.GONE);
        btnsuche.setOnClickListener(new View.OnClickListener() {
            boolean speicher = true;
            @Override
            public void onClick(View v) {
                if(!speicher){
                    calendar.setVisibility(View.GONE);
                    //calendar.getLayoutParams().height = 0;
                    List<String> input = new ArrayList<>();
                    List<String> input2 = new ArrayList<>();
                    List<String> input3 = new ArrayList<>();
                    List<String> input4 = new ArrayList<>();
                    //gr
                    for (int i = 0; i < WerteZumAnzeigen.size(); i++) {
                        input.add(dateneinlesen.getFach()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " + dateneinlesen.getStudiengang()[Integer.valueOf(WerteZumAnzeigen.get(i))]);
                        input2.add(dateneinlesen.getProfname()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " +dateneinlesen.getProfname2()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " + dateneinlesen.getSemester()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " );
                        input3.add(dateneinlesen.getDatum()[Integer.valueOf(WerteZumAnzeigen.get(i))]);
                        input4.add(WerteZumAnzeigen.get(i));

                    }// define an adapter
                    mAdapter = new MyAdapter(input, input2,input3,input4);
                    recyclerView.setAdapter(mAdapter);
                    speicher = true;
                }else {
                    calendar.setVisibility(View.VISIBLE);
                    calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
                        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                            List<String> input = new ArrayList<>();
                            List<String> input2 = new ArrayList<>();
                            List<String> input3 = new ArrayList<>();
                            List<String> input4 = new ArrayList<>();
                            //Creating editor to store values to shared preferences
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
                                    for (int i = 0; i < WerteZumAnzeigen.size(); i++) {
                                        String[] date2 = dateneinlesen.getDatum()[Integer.valueOf(WerteZumAnzeigen.get(i).toString())].toString().split(" ");
                                        if (date2[0].equals(date)) {
                                            input.add(dateneinlesen.getFach()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " + dateneinlesen.getStudiengang()[Integer.valueOf(WerteZumAnzeigen.get(i))]);
                                            input2.add(dateneinlesen.getProfname()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " + dateneinlesen.getProfname2()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " " + dateneinlesen.getSemester()[Integer.valueOf(WerteZumAnzeigen.get(i))] + " ");
                                            input3.add(dateneinlesen.getDatum()[Integer.valueOf(WerteZumAnzeigen.get(i))]);
                                            input4.add(WerteZumAnzeigen.get(i));
                                        }
                                    }// define an adapter
                                    mAdapter = new MyAdapter(input, input2,input3,input4);
                                    recyclerView.setAdapter(mAdapter);
                        }
                    });
                    speicher = false;
                }
            }
        });
        return v;
    }
}
