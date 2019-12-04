package com.Fachhochschulebib.fhb.pruefungsplaner;
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


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import com.Fachhochschulebib.fhb.pruefungsplaner.data.AppDatabase;
import com.Fachhochschulebib.fhb.pruefungsplaner.data.Pruefplan;

import java.util.ArrayList;
import java.util.List;

import static com.Fachhochschulebib.fhb.pruefungsplaner.MainActivity.mAdapter;


public class Favoritenfragment extends Fragment {
    private RecyclerView recyclerView;
    private CalendarView calendar;
    private  Button btnsuche;

    // Datenbank initialisierung
    AppDatabase roomdaten = AppDatabase.getAppDatabase(getContext());

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.terminefragment, container, false);

        //Komponenten  initialisieren für die Verwendung
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
        List<Pruefplan> pruefplandaten = roomdaten.userDao().getAll2();


        // Abfrage ob Prüfungen favorisiert wurden
        // Favorisierte Prüfungen für die Anzeige vorbereiten
        for (int i = 0; i < pruefplandaten.size(); i++) {
            if (pruefplandaten.get(i).getFavorit()) {

                String[] date2 = pruefplandaten.get(i).getDatum().toString().split(" ");
                studiengang.add(pruefplandaten.get(i).getModul() + " " + pruefplandaten.get(i).getStudiengang());
                profnamen.add(pruefplandaten.get(i).getErstpruefer() + " " + pruefplandaten.get(i).getZweitpruefer() + " " + pruefplandaten.get(i).getSemester().toString());
                //input2.add(pruefplandaten.getProfname()[Integer.valueOf(response.get(i).toString())] + " " + pruefplandaten.getSemester()[Integer.valueOf(response.get(i).toString())] + " ");
                datum.add(pruefplandaten.get(i).getDatum());
                pruefungsNr.add(pruefplandaten.get(i).getID());
            }
        }

        // define an adapter
        // übergabe der variablen an den Recyclerview Adapter, für die darstellung
        mAdapter = new MyAdapterfavorits(studiengang, profnamen, datum, pruefungsNr);
        recyclerView.setAdapter(mAdapter);


        return v;
    }
}
