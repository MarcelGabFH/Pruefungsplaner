package com.example.fhb.pruefungsplaner;


import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.content.SharedPreferences.Editor;
import android.content.*;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    static Spinner spStudiengangMain;
    static Spinner spPruef;
    static Spinner spJahr;
    String Jahr;
    String Pruefphase;

    String RueckgabeStudiengang;
    public static int i;
    public RecyclerView recyclerView;
    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    public RecyclerView.LayoutManager layoutManager;
    static GetterSetter dateneinlesen = new GetterSetter();
    dbconnect  database = new dbconnect();
    static public RecyclerView.Adapter mAdapter;
    private  Button btnclick;


    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

                List<String> WerteZumAnzeigen = dateneinlesen.ab;
                //hinzufügen von recycleview
                // use this setting to
                // improve performance if you know that changes
                // in content do not change the layout size
                // of the RecyclerView
                // use a linear layout manager
        //Context für db connect
        final Context context = getBaseContext();

        setContentView(R.layout.activity_main);
        setContentView(R.layout.start);
        Button btngo = (Button) findViewById(R.id.btnGO);
        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                database.database(context,Jahr,RueckgabeStudiengang.toString(),Pruefphase,"0");

                final SharedPreferences pref = getApplicationContext().getSharedPreferences("JSON", 0); // 0 - for private mode
                final Editor editor = pref.edit();
                editor.apply();
                String email = pref.getString("JSON", "speicher");


                //String email2 = ("[{\"Pruefform\":\"K_90\",\"Erstpruefer\":\"Teich\",\"Datum\":\"2019-02-08 07:00:00.0\",\"Semester\":\"1\",\"Zweitpruefer\":\"Haubrock\",\"Modul\":\"Elektrotechnikgrundlagen\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3012\"}","\"Pruefform\":\"K_90\",\"Erstpruefer\":\"Ohlhoff\",\"Datum\":\"2019-02-07 09:00:00.0\",\"Semester\":\"3\",\"Zweitpruefer\":\"Kohlhase\",\"Modul\":\"Mathematik III\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3011\"}","{\"Pruefform\":\"K_90\",\"Erstpruefer\":\"Schindler\",\"Datum\":\"2019-02-07 09:00:00.0\",\"Semester\":\"4\",\"Zweitpruefer\":\"Hesse\",\"Modul\":\"Mikrocontroller\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3010\"}","{\"Pruefform\":\"K_90\",\"Erstpruefer\":\"Teich\",\"Datum\":\"2019-02-07 09:00:00.0\",\"Semester\":\"2\",\"Zweitpruefer\":\"Haubrock\",\"Modul\":\"Elektro- und Messtechnik\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3009\"}","{\"Pruefform\":\"M_30\",\"Erstpruefer\":\"Bekemeier\",\"Datum\":\"2019-02-06 08:00:00.0\",\"Semester\":\"6\",\"Zweitpruefer\":\"Hilbig\",\"Modul\":\"Programmierung verteilter Systeme\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3008\"}]";
                //String email2 = "[{\"ID\":\"1\",\"Name\":\"loviscach\",\"Fach\":\"SWE\",\"Datum\":\"2005-11-11\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"},{\"ID\":\"1\",\"Name\":\"wold\",\"Fach\":\"Netzwerktechnik\",\"Datum\":\"2006-11-11\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"},{\"ID\":\"3\",\"Name\":\"Ohloff\",\"Fach\":\"Mathe 3\",\"Datum\":\"2018-10-19\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"},{\"ID\":\"3\",\"Name\":\"Ohloff\",\"Fach\":\"Mathe 3\",\"Datum\":\"2018-10-19\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"},{\"ID\":\"3\",\"Name\":\"Ohloff\",\"Fach\":\"Mathe 3\",\"Datum\":\"2018-10-19\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"},{\"ID\":\"3\",\"Name\":\"Ohloff\",\"Fach\":\"Mathe 3\",\"Datum\":\"2018-10-19\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"},{\"ID\":\"3\",\"Name\":\"Ohloff\",\"Fach\":\"Mathe 3\",\"Datum\":\"2018-10-19\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"},{\"ID\":\"3\",\"Name\":\"Ohloff\",\"Fach\":\"Mathe 3\",\"Datum\":\"2018-10-19\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"},{\"ID\":\"3\",\"Name\":\"Ohloff\",\"Fach\":\"Mathe 3\",\"Datum\":\"2018-10-19\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"}]";
                //String email2 =  "[{\"Pruefform\":\"K_90\",\"Erstpruefer\":\"Teich\",\"Datum\":\"2019-02-08 07:00:00.0\",\"Semester\":\"1\",\"Zweitpruefer\":\"Haubrock\",\"Modul\":\"Elektrotechnikgrundlagen\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3012\"},{\"Pruefform\":\"K_90\",\"Erstpruefer\":\"Ohlhoff\",\"Datum\":\"2019-02-07 09:00:00.0\",\"Semester\":\"3\",\"Zweitpruefer\":\"Kohlhase\",\"Modul\":\"Mathematik III\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3011\"},{\"Pruefform\":\"K_90\",\"Erstpruefer\":\"Schindler\",\"Datum\":\"2019-02-07 09:00:00.0\",\"Semester\":\"4\",\"Zweitpruefer\":\"Hesse\",\"Modul\":\"Mikrocontroller\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3010\"},{\"Pruefform\":\"K_90\",\"Erstpruefer\":\"Teich\",\"Datum\":\"2019-02-07 09:00:00.0\",\"Semester\":\"2\",\"Zweitpruefer\":\"Haubrock\",\"Modul\":\"Elektro- und Messtechnik\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3009\"},{\"Pruefform\":\"M_30\",\"Erstpruefer\":\"Bekemeier\",\"Datum\":\"2019-02-06 08:00:00.0\",\"Semester\":\"6\",\"Zweitpruefer\":\"Hilbig\",\"Modul\":\"Programmierung verteilter Systeme\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3008\"}]";
                dateneinlesen.GetterSetter(email);



                Intent nextIntent = new Intent(getApplicationContext(),Tabelle.class);
                startActivity(nextIntent);
                finish();
            }
        });


        Spinners spinner2 = new Spinners();
        List<String> spinnerArray2 = new ArrayList<String>();
        spinnerArray2.add("Semester wählen");
        spinnerArray2.add("Sommer");
        spinnerArray2.add("Winter");
        //anzahl der elemente
        //adapter aufruf
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                getBaseContext(), R.layout.simple_spinner_item, spinnerArray2);
        adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spPruef = (Spinner) findViewById(R.id.spPrüfungsphase);
        spPruef.setAdapter(adapter2);
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

        Spinners spinner3 = new Spinners();
        Calendar calendar = Calendar.getInstance();

        List<String> spinnerArray3 = new ArrayList<String>();

        for( int i = 0 ;i < 4; i++) {
            int thisYear = calendar.get(Calendar.YEAR);
            spinnerArray3.add(String.valueOf((thisYear-i)));

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

        Spinners spinner = new Spinners();
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
//                    Toast.makeText(getApplicationContext(), RueckgabeStudiengang.get(i).toString() +"//"+ String.valueOf(i), Toast.LENGTH_SHORT).show();
                    //txtview.setText(Prof.get(Prof.size()-1).toString() + dateneinlesen.profname[i].toString());

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    }