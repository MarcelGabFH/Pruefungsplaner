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

import android.app.Activity;
import android.arch.persistence.room.Dao;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fhb.pruefungsplaner.data.AppDatabase;
import com.example.fhb.pruefungsplaner.data.User;
import com.example.fhb.pruefungsplaner.data.UserDao;
import com.example.fhb.pruefungsplaner.data.UserDao_Impl;
import com.example.fhb.pruefungsplaner.model.RetrofitConnect;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.View.Z;

public class MainActivity extends AppCompatActivity {
    static public RecyclerView.Adapter mAdapter;


   public static String Jahr = null;
   public static String Pruefphase = null;
   public static String RueckgabeStudiengang = null;
   public static String Termin;
    //KlassenVariablen
    private Spinner spStudiengangMain;
    private Spinner spPruef;
    private Spinner spJahr;
    List<String> ID = new ArrayList<String>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Context context = getBaseContext();

        //aufrufen des startlayouts
        setContentView(R.layout.start);

        Termin = "0";
        final RadioButton rBPruefungsphase1 = (RadioButton) findViewById(R.id.rBPruefung1);
        final RadioButton rBPruefungsphase2 = (RadioButton) findViewById(R.id.rBPruefung2);


        //OK Button, hier wird die neue activity aufgerufen --> aufruf von dem layout "hauptfenster" und der Klasse Tabelle
        Button btngo = (Button) findViewById(R.id.btnGO);
        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppDatabase database2 = AppDatabase.getAppDatabase(getBaseContext());
                List<User> userdaten2 = database2.userDao().getAll2();
                Log.d("Test4", String.valueOf(userdaten2.size()));

                for (int i = 0; i < userdaten2.size(); i++) {
                    for (int j = 0; j < ID.size(); j++) {
                        if (userdaten2.get(i).getID().equals(ID.get(j))) {

                            Log.d("Test4", String.valueOf(userdaten2.get(i).getID()));
                            database2.userDao().update(true,Integer.valueOf(userdaten2.get(i).getID()));
                        }
                    }
                }// define an adapter



                if (rBPruefungsphase1.isChecked()) {
                    Termin = "0";
                } else if (rBPruefungsphase2.isChecked()) {
                    Termin = "1";
                }

                //initialisierung room database
                 AppDatabase roomdaten =  AppDatabase.getAppDatabase(getBaseContext());

                //retrofit auruf
                RetrofitConnect retrofit = new RetrofitConnect();
                retrofit.retro(getApplicationContext(),roomdaten, "2018", RueckgabeStudiengang.toString(), Pruefphase, Termin);


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
        int KalenderMonat = calendar.get(Calendar.MONTH);

        if (KalenderMonat  <= 3)
        {
            Pruefphase = "W";
        }


        if (KalenderMonat  > 3)
        {
            Pruefphase = "S";
        }


        if (KalenderMonat > 11) {

            Pruefphase = "W";
        }

        List<String> spinnerArray3 = new ArrayList<String>();
        for (int i = 0; i < 1; i++) {
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
                Jahr = "2018";
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

            boolean a = pingUrl("thor.ad.fh-bielefeld.de:8080/");


   }

    public void update2(){
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getBaseContext(), "Keine Verbindung zum Server möglich", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void update3(){
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getBaseContext(), "Prüfungen wurden aktualisiert", Toast.LENGTH_SHORT).show();
                AppDatabase database = AppDatabase.getAppDatabase(getBaseContext());

                List<User> userdaten = database.userDao().getAll2();
                List<String> validation = new ArrayList<String>();
                validation.add("0");

                Log.d("Test",String.valueOf(userdaten.size()));


                for (int i = 0; i < userdaten.size(); i++) {
                    Log.d("Test",String.valueOf(userdaten.get(i).getFavorit()));
                    if (userdaten.get(i).getFavorit()) {
                        ID.add(userdaten.get(i).getID().toString());
                        validation.add(userdaten.get(i).getValidation().toString());
                        Log.d("Test2",String.valueOf(userdaten.get(i).getValidation()));

                    }
                }// define an adapter

                database.clearAllTables();



                Termin = "0";
                final RadioButton rBPruefungsphase1 = (RadioButton) findViewById(R.id.rBPruefung1);
                final RadioButton rBPruefungsphase2 = (RadioButton) findViewById(R.id.rBPruefung2);


                if (rBPruefungsphase1.isChecked()) {
                    Termin = "0";
                } else if (rBPruefungsphase2.isChecked()) {
                    Termin = "1";
                }

                //initialisierung room database
                AppDatabase roomdaten =  AppDatabase.getAppDatabase(getBaseContext());

                //retrofit auruf


                for( int a = 1; a < validation.size();a++ ) {
                   String[] stringaufteilung = validation.get(a).split("");
                    RetrofitConnect retrofit = new RetrofitConnect();
                    retrofit.retro(getApplicationContext(),roomdaten, Jahr, stringaufteilung[5], Pruefphase, Termin);
                    Log.d("Test3",String.valueOf(stringaufteilung[5]));


                }


            }
        });

    }

    public boolean pingUrl(final String address) {

        new Thread(new Runnable() {
            public void run() {
                try {
                    final URL url = new URL("http://" + address);
                    final HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setConnectTimeout(1000 * 10); // mTimeout is in seconds
                    final long startTime = System.currentTimeMillis();
                    urlConn.connect();
                    final long endTime = System.currentTimeMillis();
                    if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        System.out.println("Time (ms) : " + (endTime - startTime));
                        System.out.println("Ping to " + address + " was success");
                        update3();


                    }
                }
                catch (final Exception e)
                {
                    update2();
                }

            }
        }).start();

        return true;
    }
}

