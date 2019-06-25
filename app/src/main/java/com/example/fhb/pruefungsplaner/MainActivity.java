package com.example.fhb.pruefungsplaner;
//////////////////////////////
// MainActivity
//
//
//
// autor:
// inhalt:  auswahl des studiengangs mit dazugehörigen Jahr und Semester
// zugriffsdatum: 02.05.19
//
//
//
//
//
//
//////////////////////////////

import android.arch.persistence.room.Dao;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.fhb.pruefungsplaner.data.AppDatabase;
import com.example.fhb.pruefungsplaner.data.User;
import com.example.fhb.pruefungsplaner.data.UserDao;
import com.example.fhb.pruefungsplaner.data.UserDao_Impl;
import com.example.fhb.pruefungsplaner.model.RetrofitConnect;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static public RecyclerView.Adapter mAdapter;
    static Pruefplaneintrag dateneinlesen = new Pruefplaneintrag();
    static AppDatabase roomdaten;

    String Jahr;
    String Pruefphase;
    String RueckgabeStudiengang;
    dbconnect dbconnect = new dbconnect();
    //KlassenVariablen
    private Spinner spStudiengangMain;
    private Spinner spPruef;
    private Spinner spJahr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Context context = getBaseContext();

        //aufrufen des startlayouts
        setContentView(R.layout.start);


        //OK Button, hier wird die neue activity aufgerufen --> aufruf von dem layout "hauptfenster" und der Klasse Tabelle
        Button btngo = (Button) findViewById(R.id.btnGO);
        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //initialisierung room database
                 roomdaten =  AppDatabase.getAppDatabase(getBaseContext());

                //retrofit auruf
                RetrofitConnect retrofit = new RetrofitConnect();
                retrofit.retro(roomdaten, Jahr, RueckgabeStudiengang.toString(), Pruefphase, "0");
                //aufruf der Webseite mit eingaben der spinner
                dbconnect.database(context, Jahr, RueckgabeStudiengang.toString(), Pruefphase, "0",dateneinlesen);

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("JSON", 0); // 0 - for private mode
                    //einlesen der daten ausdem json String und übergeben an die interne Datenbank
                   // String ausgewaehltePruefungen = pref.getString("JSON", "speicher");
                    //dateneinlesen.Pruefdaten(ausgewaehltePruefungen);
                    //aufruf der neuen activity




                Intent hauptfenster = new Intent(getApplicationContext(), Tabelle.class);
                startActivity(hauptfenster);
                finish();

                }




        });

        //definieren des Arrays jahreszeit
        List<String> jahreszeit = new ArrayList<String>();
        jahreszeit.add("Semester wählen");
        jahreszeit.add("Sommer");
        jahreszeit.add("Winter");
        //spinner füllen mit werten für Sommer/Winter
        ArrayAdapter<String> studiengang = new ArrayAdapter<String>(
                getBaseContext(), R.layout.simple_spinner_item, jahreszeit);
        studiengang.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spPruef = (Spinner) findViewById(R.id.spPrüfungsphase);
        spPruef.setAdapter(studiengang);
        spPruef.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Sommer")) {
                    Pruefphase = "S";
                }
                if (parent.getItemAtPosition(position).toString().equals("Winter")) {
                    Pruefphase = "W";
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Kalender damit das aktuelle und die letzten 4 jahre auszuwählen
        Calendar calendar = Calendar.getInstance();
        List<String> spinnerArray3 = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            int thisYear = calendar.get(Calendar.YEAR);
            spinnerArray3.add(String.valueOf((thisYear - i)));

        }

        //anzahl der elemente
        //adapter aufruf
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
                getBaseContext(), R.layout.simple_spinner_item, spinnerArray3);
        adapter3.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spJahr = (Spinner) findViewById(R.id.spDatum);
        spJahr.setAdapter(adapter3);
        spJahr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Jahr = parent.getItemAtPosition(position).toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinnerarray für die studiengänge
        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Studiengang wählen");
        spinnerArray.add("Ingenieurinformatik");
        spinnerArray.add("Elektrotechnik");
        spinnerArray.add("Regenenerative Energien");
        spinnerArray.add("Elektrotechnik Master");
        //anzahl der elemente

        //adapter aufruf
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getBaseContext(), R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spStudiengangMain = (Spinner) findViewById(R.id.spStudiengang);
        spStudiengangMain.setAdapter(adapter);
        final List<String> Studiengang = new ArrayList();

        //spinner auswahl für den studiengang
        spStudiengangMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Studiengang.add(parent.getItemAtPosition(position).toString()); //this is your selected item
                if (Studiengang.get(Studiengang.size() - 1).toString().equals("Ingenieurinformatik")) {
                    RueckgabeStudiengang = "1";
                }
                if (Studiengang.get(Studiengang.size() - 1).toString().equals("Elektrotechnik")) {
                    RueckgabeStudiengang = "2";
                }
                if (Studiengang.get(Studiengang.size() - 1).toString().equals("Regenenerative Energien")) {
                    RueckgabeStudiengang = "4";
                }
                if (Studiengang.get(Studiengang.size() - 1).toString().equals("Elektrotechnik Master")) {
                    RueckgabeStudiengang = "5";
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}

