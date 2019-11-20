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
import com.Fachhochschulebib.fhb.pruefungsplaner.data.User;
import java.util.ArrayList;
import java.util.List;

import static com.Fachhochschulebib.fhb.pruefungsplaner.MainActivity.mAdapter;


public class Favoritenfragment extends Fragment {
    private RecyclerView recyclerView;
    private CalendarView calendar;
    private  Button btnsuche;

    // Datenbank initialisierung
    AppDatabase roomdaten = AppDatabase.getAppDatabase(getContext());
    List<User>dateneinlesen = roomdaten.userDao().getAll2();


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
        List<User> userdaten = roomdaten.userDao().getAll2();


        // Abfrage ob Prüfungen favorisiert wurden
        // Favorisierte Prüfungen für die Anzeige vorbereiten
        for (int i = 0; i < userdaten.size(); i++) {
            if (userdaten.get(i).getFavorit()) {

                String[] date2 = userdaten.get(i).getDatum().toString().split(" ");
                studiengang.add(userdaten.get(i).getModul() + " " + userdaten.get(i).getStudiengang());
                profnamen.add(userdaten.get(i).getErstpruefer() + " " + userdaten.get(i).getZweitpruefer() + " " + userdaten.get(i).getSemester().toString());
                //input2.add(dateneinlesen.getProfname()[Integer.valueOf(response.get(i).toString())] + " " + dateneinlesen.getSemester()[Integer.valueOf(response.get(i).toString())] + " ");
                datum.add(userdaten.get(i).getDatum());
                pruefungsNr.add(userdaten.get(i).getID());
            }
        }

        // define an adapter
        // übergabe der variablen an den Recyclerview Adapter, für die darstellung
        mAdapter = new MyAdapterfavorits(studiengang, profnamen, datum, pruefungsNr);
        recyclerView.setAdapter(mAdapter);


        return v;
    }
}
