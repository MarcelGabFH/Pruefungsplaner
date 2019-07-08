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
import android.widget.Toast;

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


   public static String Jahr = null;
   public static String Pruefphase = null;
   public static String RueckgabeStudiengang = null;

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
                 AppDatabase roomdaten =  AppDatabase.getAppDatabase(getBaseContext());

                //retrofit auruf
                RetrofitConnect retrofit = new RetrofitConnect();
                retrofit.retro(roomdaten, Jahr, RueckgabeStudiengang.toString(), Pruefphase, "0");

                Intent hauptfenster = new Intent(getApplicationContext(), Tabelle.class);
                startActivity(hauptfenster);
                finish();

                }




        });


        Button btngo2 = (Button) findViewById(R.id.btnGO2);
        btngo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String validation = Jahr + RueckgabeStudiengang +  Pruefphase;

                update(validation);

            }




        });

        //definieren des Arrays jahreszeit
        List<String> jahreszeit = new ArrayList<String>();
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
        for (int i = 0; i < 2; i++) {
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

   public void update(String validation){

        AppDatabase database = AppDatabase.getAppDatabase(getBaseContext());
        database.clearAllTables();
        Toast.makeText(getBaseContext(), "Studiengang wurde aktualisiert", Toast.LENGTH_SHORT).show();

   }
}

