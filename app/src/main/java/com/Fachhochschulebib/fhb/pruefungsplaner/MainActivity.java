package com.Fachhochschulebib.fhb.pruefungsplaner;
//////////////////////////////
// MainActivity
//
//
//
// autor:
// inhalt:  auswahl des studiengangs mit dazugehörigen pruefJahr und Semester
// zugriffsdatum: 02.05.19
//
//
//
//
//
//
//////////////////////////////

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Fachhochschulebib.fhb.pruefungsplaner.data.AppDatabase;
import com.Fachhochschulebib.fhb.pruefungsplaner.data.Pruefplan;
import com.Fachhochschulebib.fhb.pruefungsplaner.model.RetrofitConnect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


public class MainActivity extends AppCompatActivity {
    static public RecyclerView.Adapter mAdapter;

   public static String pruefJahr = null;
   public static String aktuellePruefphase = null;
   public static String rueckgabeStudiengang = null;
   public static String aktuellerTermin;
    //KlassenVariablen
    private JSONArray mainObject;
    final List<String> spinnerArray = new ArrayList<String>();
    private Spinner spStudiengangMain;
    List<String> id = new ArrayList<String>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final Context context = getBaseContext();

        //aufrufen des startlayouts
        setContentView(R.layout.start);

        pingpruefperiode();


        //Zugriffrechte für den GoogleKalender
        final int callbackId = 42;
        checkPermission(callbackId, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR);



        //OK Button, hier wird die neue activity aufgerufen --> aufruf von dem layout "hauptfenster" und der Klasse Tabelle
        Button btngo = (Button) findViewById(R.id.btnGO);
        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerArray.size() > 1) {
                    AppDatabase databasePruefplan = AppDatabase.getAppDatabase(getBaseContext());
                    List<Pruefplan> pruefplandaten = databasePruefplan.userDao().getAll2();
                    Log.d("Test4", String.valueOf(pruefplandaten.size()));

                    for (int i = 0; i < pruefplandaten.size(); i++) {
                        for (int j = 0; j < id.size(); j++) {
                            if (pruefplandaten.get(i).getID().equals(id.get(j))) {

                                Log.d("Test4", String.valueOf(pruefplandaten.get(i).getID()));
                                databasePruefplan.userDao().update(true, Integer.valueOf(pruefplandaten.get(i).getID()));
                            }
                        }
                    }// define an adapter


                    //initialisierung room database
                    AppDatabase datenbank = AppDatabase.getAppDatabase(getBaseContext());

                    //retrofit auruf
                    RetrofitConnect retrofit = new RetrofitConnect();
                    aktuellerTermin = "0";
                    retrofit.retro(getApplicationContext(), datenbank, pruefJahr, rueckgabeStudiengang, aktuellePruefphase, aktuellerTermin);


                    Intent hauptfenster = new Intent(getApplicationContext(), Tabelle.class);
                    startActivity(hauptfenster);
                }

            }


        });


        Button btngo2 = (Button) findViewById(R.id.btnGO2);
        btngo2.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){

                String validation = pruefJahr + rueckgabeStudiengang + aktuellePruefphase;

                Checkverbindung(validation);

            }
        });

        //definieren des Arrays jahreszeit
        List<String> jahreszeit = new ArrayList<String>();
        jahreszeit.add("Sommer");
        jahreszeit.add("Winter");


        //spinner füllen mit werten für Sommer/Winter
        final ArrayAdapter<String> studiengang = new ArrayAdapter<String>(
                getBaseContext(), R.layout.simple_spinner_item, jahreszeit);
        studiengang.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);


        //Kalender damit das aktuelle und die letzten 4 jahre auszuwählen

        Calendar calendar = Calendar.getInstance();
        int kalenderMonat = calendar.get(Calendar.MONTH );
        Log.d("Output Monat",String.valueOf(kalenderMonat));


        if (kalenderMonat  <= 3)
        {
            aktuellePruefphase = "W";
            List<String> spinnerArray3 = new ArrayList<String>();
            for (int i = 0; i < 1; i++) {
                int thisYear = calendar.get(Calendar.YEAR);
                pruefJahr = String.valueOf(thisYear);

            }
        }


        if (kalenderMonat  > 3)
        {
            aktuellePruefphase = "S";
            List<String> spinnerArray3 = new ArrayList<String>();
            for (int i = 0; i < 1; i++) {
                int thisYear = calendar.get(Calendar.YEAR);
                pruefJahr = String.valueOf(thisYear);
            }
        }


        if (kalenderMonat >= 9) {

            aktuellePruefphase = "W";
            List<String> spinnerArray3 = new ArrayList<String>();
            for (int i = 0; i < 1; i++) {
                int thisYear = calendar.get(Calendar.YEAR);
                pruefJahr = String.valueOf(thisYear +1);


            }
        }



        //anzahl der elemente
        //adapter aufruf

        try {
            Checkverbindung("test");
            SharedPreferences pruefperiode = getApplicationContext().getSharedPreferences("pruefperiode", 0);
            //Creating editor to store uebergebeneModule to shared preferencess
            String strJson = pruefperiode.getString("pruefperiode", "0");
            TextView txtpruefperiode = (TextView) findViewById(R.id.txtpruefperiode);
            //second parameter is necessary ie.,Value to return if this preference does not exist.
            if (strJson != null) {
                txtpruefperiode.setText(strJson);
            }

        }
        catch(Exception e) {
            SharedPreferences pruefperiode = getApplicationContext().getSharedPreferences("studiengaenge", 0);
            //Creating editor to store uebergebeneModule to shared preferencess
            String strJson = pruefperiode.getString("studiengaenge", "0");
            //second parameter is necessary ie.,Value to return if this preference does not exist.

            if (strJson != null) {
                try {
                    mainObject = new JSONArray(strJson);
                    for (int i = 0; i < mainObject.length(); i++) {
                        JSONObject json = mainObject.getJSONObject(i);
                        spinnerArray.add(json.get("SGName").toString());
                        uebergabeAnSpinner();
                    }
                } catch (Exception b) {

                }

            }

            pruefperiode = getApplicationContext().getSharedPreferences("pruefperiode", 0);
            //Creating editor to store uebergebeneModule to shared preferencess
            strJson = pruefperiode.getString("pruefperiode", "0");
            TextView txtpruefperiode = (TextView) findViewById(R.id.txtpruefperiode);
            //second parameter is necessary ie.,Value to return if this preference does not exist.
            if (strJson != null) {
                txtpruefperiode.setText(strJson);
            }

        }




    }

   public void Checkverbindung(String validation){

            boolean aktuelleurl = pingUrl("thor.ad.fh-bielefeld.de:8080/PruefplanApplika/webresources/entities.studiengang");


   }

    public void Keineverbindung(){
        new Thread(new Runnable() {
            public void run() {
                Toast.makeText(getBaseContext(), "Keine Verbindung zum Server möglich", Toast.LENGTH_SHORT).show();
            }
        });

    }


    //darstellen der Studiengänge in der Spinner-Komponente.
    public void uebergabeAnSpinner(){
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                //Toast.makeText(getBaseContext(), "Prüfungen wurden aktualisiert", Toast.LENGTH_SHORT).show();
                //spinnerarray für die studiengänge


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
                        for (int i = 0 ; i < spinnerArray.size(); i++)
                        {
                            if (parent.getItemAtPosition(position).toString().equals(spinnerArray.get(i))) {
                                try{
                                    JSONObject object = mainObject.getJSONObject(i);
                                    rueckgabeStudiengang = object.get("sgid").toString();
                                    Log.d("Output studiengang", rueckgabeStudiengang.toString());

                                }
                                catch (Exception e)
                                {

                                }
                            }
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        });
    }


    //Methode zum überprüfen der studiengänge
    public boolean pingUrl(final String address) {
        //eigenständiger Thread, weil die Abfrage Asynchron ist
        new Thread(new Runnable() {
                public void run() {
                    // Die studiengänge werden in einer shared preferences Variable gespeichert
                SharedPreferences.Editor studiengangEditor;
                SharedPreferences studiengaenge = getApplicationContext().getSharedPreferences("studiengaenge", 0);
                //Creating editor to store uebergebeneModule to shared preferences

                    //Verbindungsaufbau zum Webserver
                try {
                    StringBuilder result = new StringBuilder();
                    final URL url = new URL("http://" + address);
                    final HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                   // urlConn.setRequestProperty("Content-Type", "application/json");
                    //urlConn.setRequestProperty("Accept", "application/json");
                    Log.d("Output studiengang","test");
                    urlConn.setConnectTimeout(100 * 10); // mTimeout is in seconds
                    final long startTime = System.currentTimeMillis();
                    Log.d("Output studiengang","test");
                    urlConn.connect();
                    final long endTime = System.currentTimeMillis();
                    if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        System.out.println("Time (ms) : " + (endTime - startTime));
                        System.out.println("Ping to " + address + " was success");
                        //uebergabeAnSpinner();
                    }

                    //Parsen von den  erhaltene Werte
                    Log.d("Output studiengang","test2");
                    InputStream in = new BufferedInputStream(urlConn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    //create JSON
                    Log.d("Output studiengang","test3");
                    Log.d("Output studiengang",String.valueOf(result.toString()));
                    JSONObject jsonObj = null;
                    try {
                        jsonObj = XML.toJSONObject(String.valueOf(result));
                        Log.d("Output studiengang",jsonObj.toString());

                    } catch (JSONException e) {
                        Log.e("JSON exception", e.getMessage());
                        e.printStackTrace();
                    }

                    Iterator x = jsonObj.keys();
                    JSONArray jsonArray = new JSONArray();
                    Log.d("Output studiengang","test5");
                    while (x.hasNext()){
                        String key = (String) x.next();
                        jsonArray.put(jsonObj.get(key));
                    }

                    Log.d("Output studiengang","test 6");

                    //Werte von JSONARRay in JSONObject konvertieren
                    JSONArray object2 = new JSONArray();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        //spinnerArray.add(object.getString("SGName"));  //This will have the value of the studiengang
                        //String studiengangid = object.getString("sgid");  //This will have the value of the id
                       Log.d("Output studiengang",object.get("studiengang").toString());
                        object2.put(object.get("studiengang"));
                        Log.d("Output studiengang",String.valueOf(object2.length()));
                    }

                    String a = object2.toString();
                    String b = a.substring(1,a.length()-1);

                    Log.d("Output zeile 394",b);

                    mainObject = new JSONArray(b);

                    for(int i= 0 ; i< mainObject.length();i++) {
                        JSONObject json = mainObject.getJSONObject(i);
                        spinnerArray.add(json.get("SGName").toString());
                    }

                    // Werte Speichern für die offline Verwendung
                    Log.d("Output studiengang",mainObject.get(0).toString());
                    studiengangEditor = studiengaenge.edit();
                    //second parameter is necessary ie.,Value to return if this preference does not exist.
                        try {
                                studiengangEditor.clear();
                                studiengangEditor.apply();
                                studiengangEditor.putString("studiengaenge", b);
                                studiengangEditor.apply();
                        } catch (Exception e) {

                        }


                    uebergabeAnSpinner();


            }
                catch (final Exception e)
                {
                    String strStudiengang = studiengaenge.getString("studiengaenge", "0");
                    //second parameter is necessary ie.,Value to return if this preference does not exist.
                    Log.d("Output exception",strStudiengang);
                    if (strStudiengang != null) {
                        try{
                            mainObject = new JSONArray(strStudiengang);
                            for(int i= 0 ; i< mainObject.length();i++) {
                                JSONObject json = mainObject.getJSONObject(i);
                                spinnerArray.add(json.get("SGName").toString());
                                uebergabeAnSpinner();
                            }
                        }catch (Exception b)
                        {

                        }

                    }

                    Keineverbindung();
                }




            }
        }).start();

        return true;
    }

    //Methode zum Abfragen der Aktuellen Prüfperiode
    public boolean pingpruefperiode() {

        new Thread(new Runnable() {
            SharedPreferences.Editor mEditor;
            SharedPreferences mSharedPreferencesperiode = getApplicationContext().getSharedPreferences("pruefperiode", 0);
            //Creating editor to store uebergebeneModule to shared preferencess
            public void run() {

                try {
                    StringBuilder result = new StringBuilder();
                    String address = "thor.ad.fh-bielefeld.de:8080/PruefplanApplika/webresources/entities.pruefperioden";
                    final URL url = new URL("http://" + address);
                    final HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                    // urlConn.setRequestProperty("Content-Type", "application/json");
                    //urlConn.setRequestProperty("Accept", "application/json");
                    Log.d("Output studiengang","test");
                    urlConn.setConnectTimeout(1000 * 10); // mTimeout is in seconds
                    long startTime = System.currentTimeMillis();

                    Log.d("Output studiengang","test");
                    urlConn.connect();
                    long endTime = System.currentTimeMillis();
                    if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        System.out.println("Time (ms) : " + (endTime - startTime));
                        System.out.println("Ping to " + address + " was success");
                        //uebergabeAnSpinner();


                    }
                    Log.d("Output studiengang","test2");


                    InputStream in = new BufferedInputStream(urlConn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    Log.d("Output pruefperiode","test3");


                    Log.d("Output pruefperiode",String.valueOf(result.toString()));

                    JSONObject jsonObj = null;

                    try {
                       jsonObj = XML.toJSONObject(String.valueOf(result));
                        Log.d("Output pruefperiode",jsonObj.toString());

                    } catch (JSONException e) {
                        Log.e("JSON exception", e.getMessage());
                        e.printStackTrace();
                    }

                    Iterator x = jsonObj.keys();
                    JSONArray jsonArray = new JSONArray();
                    Log.d("Output pruefperiode","test5");
                    while (x.hasNext()){
                        String key = (String) x.next();
                        jsonArray.put(jsonObj.get(key));
                    }

                    Log.d("Output pruefperiode","test 6");

                    JSONArray object2 = new JSONArray();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        //spinnerArray.add(object.getString("SGName"));  //This will have the value of the studiengang
                        //String studiengangid = object.getString("sgid");  //This will have the value of the id
                        Log.d("Output pruefperiode",object.get("pruefperioden").toString());

                        object2.put(object.get("pruefperioden"));
                        Log.d("Output pruefperiode",String.valueOf(object2.length()));
                    }
                    String a = object2.toString();
                    String b = a.substring(1,a.length()-1);
                    JSONArray mainObject2 = new JSONArray(b);
                    JSONObject pruefperiodeTermin = mainObject2.getJSONObject(mainObject2.length()-1);
                    String pruefperiode = pruefperiodeTermin.get("startDatum").toString();
                   String[] arrayPruefperiode=  pruefperiode.split("T");
                    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                    Date inputDate = fmt.parse(arrayPruefperiode[0]);

                    //erhaltenes Datum Parsen als Datum
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(inputDate);
                    int year = calendar.get(Calendar.YEAR);
                    //Add one to month {0 - 11}
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    calendar.add(Calendar.DATE, 14);
                    int year2 = calendar.get(Calendar.YEAR);
                    //Add one to month {0 - 11}
                    int month2 = calendar.get(Calendar.MONTH) + 1;
                    int day2 = calendar.get(Calendar.DAY_OF_MONTH);
                    //String Prüfperiode zum Anzeigen
                    String pruefperiodedatum;
                    pruefperiodedatum = "Aktuelle Prüfungsphase: \n " +String.valueOf(day) +"."+ String.valueOf(month) +"."+ String.valueOf(year) +" bis "+ String.valueOf(day2) +"."+ String.valueOf(month2) +"."+ String.valueOf(year2) ;  // number of days to add;
                    //Log.d("Output pruefperiode",pruefperiodedatum);
                    // Prüfperiode für die offline Verwendung speichern
                    mEditor = mSharedPreferencesperiode.edit();
                    String strJson = mSharedPreferencesperiode.getString("pruefperiode", " ");
                    //second parameter is necessary ie.,Value to return if this preference does not exist.
                    if (strJson != null) {
                        try {
                            if (strJson.equals(pruefperiodedatum))
                            {

                            }
                            else{
                                mEditor.clear();
                                mEditor.apply();
                                mEditor.putString("pruefperiode", pruefperiodedatum);
                                mEditor.apply();
                            }

                        } catch (Exception e) {
                        }
                    }
                }
                catch (final Exception e)
                {
                    //Keineverbindung();
                }
            }
        }).start();

        return true;
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Beenden")
                .setMessage("Möchten Sie die App schließen?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                    }
                }).create().show();
    }


    private void checkPermission(int callbackId, String... permissionsId) {
        boolean permissions = true;
        for (String p : permissionsId) {
            permissions = permissions && ContextCompat.checkSelfPermission(this, p) == PERMISSION_GRANTED;
        }

        if (!permissions)
            ActivityCompat.requestPermissions(this, permissionsId, callbackId);
    }


}

