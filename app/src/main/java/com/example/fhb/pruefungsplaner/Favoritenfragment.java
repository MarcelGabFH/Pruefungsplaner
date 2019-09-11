package com.example.fhb.pruefungsplaner;
//////////////////////////////
// favoritenfragment
//
//
//
// autor:
// inhalt:  stelllt die favorisierten prüfungen bereit.
// zugriffsdatum: 02.05.19
//
//
//
//
//
//
//////////////////////////////

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

import com.example.fhb.pruefungsplaner.data.AppDatabase;
import com.example.fhb.pruefungsplaner.data.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.example.fhb.pruefungsplaner.MainActivity.RueckgabeStudiengang;
import static com.example.fhb.pruefungsplaner.MainActivity.mAdapter;


public class Favoritenfragment extends Fragment {
    SharedPreferences mSharedPreferences;
    private FragmentTransaction ft;
    private RecyclerView recyclerView;
    private CalendarView calendar;
    private  Button btnsuche;
    private String date;
    private String month2;
    private String day2;
    private String year2;




    AppDatabase roomdaten = AppDatabase.getAppDatabase(getContext());
    List<User>dateneinlesen = roomdaten.userDao().getAll2();


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.terminefragment, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView4);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        calendar = (CalendarView) v.findViewById(R.id.caCalender);
        btnsuche = (Button) v.findViewById(R.id.btnDatum);
        calendar.setVisibility(View.GONE);
        List<String> studiengang = new ArrayList<>();
        List<String> profnamen = new ArrayList<>();
        List<String> datum = new ArrayList<>();
        List<String> pruefungsNr = new ArrayList<>();
        btnsuche.setVisibility(View.INVISIBLE);
        List<User> userdaten = roomdaten.userDao().getAll2();

        for (int i = 0; i < userdaten.size(); i++) {
            if (userdaten.get(i).getFavorit()) {

                String[] date2 = userdaten.get(i).getDatum().toString().split(" ");
                studiengang.add(userdaten.get(i).getModul() + " " + userdaten.get(i).getStudiengang());
                profnamen.add(userdaten.get(i).getErstpruefer() + " " + userdaten.get(i).getZweitpruefer() + " " + userdaten.get(i).getSemester().toString());
                //input2.add(dateneinlesen.getProfname()[Integer.valueOf(response.get(i).toString())] + " " + dateneinlesen.getSemester()[Integer.valueOf(response.get(i).toString())] + " ");
                datum.add(userdaten.get(i).getDatum());
                pruefungsNr.add(userdaten.get(i).getID());
            }
        }// define an adapter
        mAdapter = new MyAdapterfavorits(studiengang, profnamen, datum, pruefungsNr);
        recyclerView.setAdapter(mAdapter);



        btnsuche.setOnClickListener(new View.OnClickListener() {
            boolean speicher = false ;
            @Override
            public void onClick(View v) {
                if(speicher){
                    calendar.setVisibility(View.GONE);
                    //calendar.getLayoutParams().height = 0;
                    speicher = false;
                    List<String> studiengang = new ArrayList<>();
                    List<String> profnamen = new ArrayList<>();
                    List<String> datum = new ArrayList<>();
                    List<String> pruefungsNR = new ArrayList<>();
                    //Creating editor to store values to shared preferences
                    List<User> userdaten = roomdaten.userDao().getAll2();

                            for (int i = 0; i < userdaten.size(); i++) {
                                if (userdaten.get(i).getFavorit()) {


                                    studiengang.add(userdaten.get(i).getModul()+ " " + userdaten.get(i).getStudiengang());
                                    profnamen.add(userdaten.get(i).getErstpruefer() + " " + userdaten.get(i).getZweitpruefer() + " " + userdaten.get(i).getSemester().toString());
                                    datum.add(userdaten.get(i).getDatum());
                                    pruefungsNR.add(userdaten.get(i).getID());
                                }
                            }// define an adapter

                            mAdapter = new MyAdapterfavorits(studiengang, profnamen,datum,pruefungsNR);
                            recyclerView.setAdapter(mAdapter);


                }else {
                    calendar.setVisibility(View.GONE);
                    speicher = true;
                }
            }
        });

        return v;
    }
}
