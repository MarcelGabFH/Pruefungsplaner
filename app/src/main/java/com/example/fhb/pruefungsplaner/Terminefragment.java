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


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.terminefragment, container, false);
        //setContentView(R.layout.hauptfenster);
        List<String> WerteZumAnzeigen = dateneinlesen.getab();
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

        final  ViewGroup.LayoutParams height  = recyclerView.getLayoutParams();
        final  ViewGroup.LayoutParams height2  = calendar.getLayoutParams();
        final ViewGroup.LayoutParams height3  = calendar.getLayoutParams();

        btnsuche.setOnClickListener(new View.OnClickListener() {
            boolean speicher = false ;


            @Override

            public void onClick(View v) {

                if(!speicher){
                    calendar.setVisibility(View.GONE);
                    //calendar.getLayoutParams().height = 0;


                    speicher = true;
                }else {
                    calendar.setVisibility(View.VISIBLE);


                    speicher = false;
                }
            }

        });

        return v;
    }

}
