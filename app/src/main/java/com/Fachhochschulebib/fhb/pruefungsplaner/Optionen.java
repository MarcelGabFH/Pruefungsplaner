package com.Fachhochschulebib.fhb.pruefungsplaner;


//////////////////////////////
// Optionen
//
//
//
// autor:
// inhalt:  Abfragen ob prüfungen zum Kalender hinzugefügt werden sollen und login
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.Fachhochschulebib.fhb.pruefungsplaner.data.AppDatabase;
import com.Fachhochschulebib.fhb.pruefungsplaner.data.User;
import com.Fachhochschulebib.fhb.pruefungsplaner.model.RetrofitConnect;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.Fachhochschulebib.fhb.pruefungsplaner.MainActivity.Jahr;
import static com.Fachhochschulebib.fhb.pruefungsplaner.MainActivity.Pruefphase;
import static com.Fachhochschulebib.fhb.pruefungsplaner.MainActivity.RueckgabeStudiengang;
import static com.Fachhochschulebib.fhb.pruefungsplaner.MainActivity.Termin;

public class Optionen extends Fragment {
    private boolean speicher;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences.Editor mEditorAdresse;
    private JSONArray response;
    static EditText txtAdresse;
    public static List<String> ID = new ArrayList<String>();




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.optionfragment, container, false);



        //Button zum updaten der Prüfungen
        Button btngo2 = (Button) v.findViewById(R.id.btnupdate);
        btngo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String validation = Jahr + RueckgabeStudiengang +  Pruefphase;

                update(validation);

            }




        });


        //layout Komponenten
        Button btnDb = (Button) v.findViewById(R.id.btnDB);
        Button btnFav = (Button) v.findViewById(R.id.btnFav);
        Switch SWgooglecalender = (Switch) v.findViewById(R.id.switch2);
        txtAdresse = (EditText) v.findViewById(R.id.txtAdresse);
        //holder.zahl1 = position;


        SharedPreferences mSharedPreferences = v.getContext().getSharedPreferences("json8", 0);
        //Creating editor to store values to shared preferences
        mEditor = mSharedPreferences.edit();
        SharedPreferences mSharedPreferencesAdresse = v.getContext().getSharedPreferences("Server-Adresse", 0);
        //Creating editor to store values to shared preferences
        mEditorAdresse = mSharedPreferencesAdresse.edit();
        txtAdresse.setText(mSharedPreferencesAdresse.getString("Server-Adresse2","http://thor.ad.fh-bielefeld.de:8080/"));


        response = new JSONArray();
        String strJson = mSharedPreferences.getString("jsondata2", "0");
        //second parameter is necessary ie.,Value to return if this preference does not exist.
        if (strJson != null) {
            try {
                response = new JSONArray(strJson);
            } catch (JSONException e) {

            }
        }

        int i;
        speicher = false;
        for (i = 0; i < response.length(); i++) {
            {
                try {
                    if (response.get(i).toString().equals("1")) {
                        SWgooglecalender.setChecked(true);
                        speicher = true;
                    }
                } catch (JSONException e) {

                }
            }
        }
        if (!speicher) {
        }


        //Abfrage ob der Google kalender Ein/Ausgeschaltet ist
        SWgooglecalender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked) {
                    mEditor.clear();
                    mEditor.apply();
                    response.put("1");
                    mEditor.putString("jsondata2", response.toString());
                    mEditor.apply();
                    Toast.makeText(v.getContext(), "Prüfungen werden jetzt zum Kalender hinzugefügt", Toast.LENGTH_SHORT).show();
                }

                if (!isChecked) {
                    mEditor.clear().apply();
                    mEditor.remove("jsondata2").apply();
                }

            }
        });

        //Change Listener für die Serveradresse
        //speichert den neu eingegebenen Wert
        txtAdresse.addTextChangedListener(new TextWatcher() {
            boolean validate = false;
            @Override
            public void afterTextChanged(Editable s) {

                if (validate) {
                    mEditorAdresse.clear();
                    mEditorAdresse.apply();
                    mEditorAdresse.putString("Server-Adresse2", txtAdresse.getText().toString());
                    mEditorAdresse.apply();
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
               if(s.subSequence(0,3).equals("http")){
                   validate = true;

               }
            }
        });

        //interne DB löschen
        btnDb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppDatabase database2 = AppDatabase.getAppDatabase(v.getContext());
                Log.d("Test", "interne Db löschen");
                database2.clearAllTables();

                Toast.makeText(v.getContext(), "Datenbank gelöscht", Toast.LENGTH_SHORT).show();

            }
        });

        //Favoriten Löschen
        btnFav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppDatabase database2 = AppDatabase.getAppDatabase(v.getContext());
                List<User> userdaten2 = database2.userDao().getAll2();
                for (int i = 0; i < userdaten2.size(); i++) {
                        if (userdaten2.get(i).getFavorit()) {

                            Log.d("Test favoriten löschen", String.valueOf(userdaten2.get(i).getID()));
                            database2.userDao().update(false,Integer.valueOf(userdaten2.get(i).getID()));
                            Toast.makeText(v.getContext(), "Favorisierte Prüfungen gelöscht", Toast.LENGTH_SHORT).show();

                        }
                    }

                // define an adapter

            }
        });



        return v;


    }

    public void update(String validation){

        boolean a = pingUrl("thor.ad.fh-bielefeld.de:8080/");
        //boolean a = pingUrl("192.168.178.39:44631/PruefplanApplika/");
       // http://localhost:44631/PruefplanApplika/

    }

    //Methode zum Anzeigen das keine Verbindungs zum Server möglich ist
    public void update2(){
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getContext(), "Keine Verbindung zum Server möglich", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Methode zum aktualiseren der Prüfungen
    //ID der gespeicherten Prüfungen wird gespeichert und dann wird die Datenbank gelöscht
    // dann werden die Prüfungen erneut vom Webserver geladen und die Prüfungen mit
    // den gespeicherten IDs favorisiert
    public void update3(){
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getContext(), "Prüfungen wurden aktualisiert", Toast.LENGTH_SHORT).show();
                AppDatabase database = AppDatabase.getAppDatabase(getContext());

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
                //initialisierung room database
                AppDatabase roomdaten =  AppDatabase.getAppDatabase(getContext());
                //retrofit auruf
                for( int a = 1; a < validation.size();a++ ) {
                    String[] stringaufteilung = validation.get(a).split("");
                    RetrofitConnect retrofit = new RetrofitConnect();
                    retrofit.retro(getContext(),roomdaten, Jahr, stringaufteilung[5], Pruefphase, Termin);
                    Log.d("Test3",String.valueOf(stringaufteilung[5]));
                }
            }
        });

    }


    //Verbindungsaufbau zum Webserver
    //Überprüfung ob Webserver erreichbar
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



    public void insertfav() {


    }




}

