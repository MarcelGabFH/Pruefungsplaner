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
import com.Fachhochschulebib.fhb.pruefungsplaner.data.Pruefplan;
import com.Fachhochschulebib.fhb.pruefungsplaner.model.RetrofitConnect;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.Fachhochschulebib.fhb.pruefungsplaner.MainActivity.pruefJahr;
import static com.Fachhochschulebib.fhb.pruefungsplaner.MainActivity.aktuellePruefphase;
import static com.Fachhochschulebib.fhb.pruefungsplaner.MainActivity.rueckgabeStudiengang;
import static com.Fachhochschulebib.fhb.pruefungsplaner.MainActivity.aktuellerTermin;

public class Optionen extends Fragment {
    private boolean speicher;
    private SharedPreferences.Editor mEditorGoogleKalender;
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

                String validation = pruefJahr + rueckgabeStudiengang + aktuellePruefphase;

                updatePruefplan(validation);

            }




        });


        //layout Komponenten
        Button btnDb = (Button) v.findViewById(R.id.btnDB);
        Button btnFav = (Button) v.findViewById(R.id.btnFav);
        Button btnGoogleloeschen = (Button) v.findViewById(R.id.btnCalClear);
        Button btnGoogleupdate = (Button) v.findViewById(R.id.btnGoogleUpdate);
        Switch SWgooglecalender = (Switch) v.findViewById(R.id.switch2);
        txtAdresse = (EditText) v.findViewById(R.id.txtAdresse);
        //holder.zahl1 = position;


        SharedPreferences serverAdresse = v.getContext().getSharedPreferences("json8", 0);
        //Creating editor to store uebergebeneModule to shared preferences
        mEditorGoogleKalender = serverAdresse.edit();
        SharedPreferences mSharedPreferencesAdresse = v.getContext().getSharedPreferences("Server-Adresse", 0);
        //Creating editor to store uebergebeneModule to shared preferences
        mEditorAdresse = mSharedPreferencesAdresse.edit();
        txtAdresse.setText(mSharedPreferencesAdresse.getString("Server-Adresse2","http://thor.ad.fh-bielefeld.de:8080/"));


        response = new JSONArray();
        String strServerAdresse = serverAdresse.getString("jsondata2", "0");
        //second parameter is necessary ie.,Value to return if this preference does not exist.
        if (strServerAdresse != null) {
            try {
                response = new JSONArray(strServerAdresse);
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
                    mEditorGoogleKalender.clear();
                    mEditorGoogleKalender.apply();
                    response.put("1");
                    mEditorGoogleKalender.putString("jsondata2", response.toString());
                    mEditorGoogleKalender.apply();
                    Toast.makeText(v.getContext(), "Prüfungen werden jetzt zum Kalender hinzugefügt", Toast.LENGTH_SHORT).show();
                }

                if (!isChecked) {
                    mEditorGoogleKalender.clear().apply();
                    mEditorGoogleKalender.remove("jsondata2").apply();
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
                AppDatabase datenbank = AppDatabase.getAppDatabase(v.getContext());
                Log.d("Test", "interne Db löschen");
                datenbank.clearAllTables();

                Toast.makeText(v.getContext(), "Datenbank gelöscht", Toast.LENGTH_SHORT).show();

            }
        });

        //Google Kalender einträge löschen
        btnGoogleloeschen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               kalenderLöschen();
                Toast.makeText(v.getContext(), "Google Kalender Einträge gelöscht", Toast.LENGTH_SHORT).show();

            }
        });

        //Google Kalender einträge löschen
        btnGoogleupdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                kalenderUpdate();
                Toast.makeText(v.getContext(), "Google Kalender aktualisiert", Toast.LENGTH_SHORT).show();

            }
        });

        //Favoriten Löschen
        btnFav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppDatabase datenbank = AppDatabase.getAppDatabase(v.getContext());
                List<Pruefplan> pruefplandaten = datenbank.userDao().getAll2();
                for (int i = 0; i < pruefplandaten.size(); i++) {
                        if (pruefplandaten.get(i).getFavorit()) {

                            Log.d("Test favoriten löschen", String.valueOf(pruefplandaten.get(i).getID()));
                            datenbank.userDao().update(false,Integer.valueOf(pruefplandaten.get(i).getID()));
                            Toast.makeText(v.getContext(), "Favorisierte Prüfungen gelöscht", Toast.LENGTH_SHORT).show();

                        }
                    }

                // define an adapter

            }
        });



        return v;


    }

    public void updatePruefplan(String validation){

        boolean a = pingUrl("thor.ad.fh-bielefeld.de:8080/");
        //boolean a = pingUrl("192.168.178.39:44631/PruefplanApplika/");
       // http://localhost:44631/PruefplanApplika/

    }

    //Methode zum Anzeigen das keine Verbindungs zum Server möglich ist
    public void keineVerbindung(){
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getContext(), "Keine Verbindung zum Server möglich", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Methode zum aktualiseren der Prüfungen
    //id der gespeicherten Prüfungen wird gespeichert und dann wird die Datenbank gelöscht
    // dann werden die Prüfungen erneut vom Webserver geladen und die Prüfungen mit
    // den gespeicherten IDs favorisiert
    public void pruefplanAktualisieren(){
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getContext(), "Prüfungen wurden aktualisiert", Toast.LENGTH_SHORT).show();
                AppDatabase database = AppDatabase.getAppDatabase(getContext());

                List<Pruefplan> pruefplanDaten = database.userDao().getAll2();
                List<String> validation = new ArrayList<String>();

                validation.add("0");

                Log.d("Test",String.valueOf(pruefplanDaten.size()));


                for (int i = 0; i < pruefplanDaten.size(); i++) {
                    Log.d("Test",String.valueOf(pruefplanDaten.get(i).getFavorit()));
                    if (pruefplanDaten.get(i).getFavorit()) {
                        ID.add(pruefplanDaten.get(i).getID().toString());
                        validation.add(pruefplanDaten.get(i).getValidation().toString());
                        Log.d("Test2",String.valueOf(pruefplanDaten.get(i).getValidation()));
                    }
                }// define an adapter
                database.clearAllTables();
                aktuellerTermin = "0";
                //initialisierung room database
                AppDatabase roomdaten =  AppDatabase.getAppDatabase(getContext());
                //retrofit auruf
                for( int a = 1; a < validation.size();a++ ) {
                    String[] stringaufteilung = validation.get(a).split("");
                    RetrofitConnect retrofit = new RetrofitConnect();
                    retrofit.retro(getContext(),roomdaten, pruefJahr, stringaufteilung[5], aktuellePruefphase, aktuellerTermin);
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
                        pruefplanAktualisieren();


                    }
                }
                catch (final Exception e)
                {
                    keineVerbindung();
                }

            }
        }).start();

        return true;
    }



    public void insertfav() {


    }

    public void kalenderLöschen(){
        CheckGoogleCalendar cal = new CheckGoogleCalendar();
        cal.setCtx(getContext());
        cal.clearCal();

        try {

        }catch (Exception e)
        {}


    }

    public void kalenderUpdate(){
        CheckGoogleCalendar cal = new CheckGoogleCalendar();
        cal.setCtx(getContext());
        cal.updateCal();



    }



}

