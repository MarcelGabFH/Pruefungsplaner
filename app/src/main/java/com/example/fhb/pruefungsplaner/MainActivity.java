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

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    static Spinner spStudiengangMain;
    static final List<Integer> RueckgabeStudiengang = new ArrayList<>();
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
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("JSON", 0); // 0 - for private mode
        final Editor editor = pref.edit();



                editor.commit();

               String email = pref.getString("JSON", "speicher");
               //Toast.makeText(this, email, Toast.LENGTH_LONG).show();
                //String email2 = ("[{\"Pruefform\":\"K_90\",\"Erstpruefer\":\"Teich\",\"Datum\":\"2019-02-08 07:00:00.0\",\"Semester\":\"1\",\"Zweitpruefer\":\"Haubrock\",\"Modul\":\"Elektrotechnikgrundlagen\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3012\"}","\"Pruefform\":\"K_90\",\"Erstpruefer\":\"Ohlhoff\",\"Datum\":\"2019-02-07 09:00:00.0\",\"Semester\":\"3\",\"Zweitpruefer\":\"Kohlhase\",\"Modul\":\"Mathematik III\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3011\"}","{\"Pruefform\":\"K_90\",\"Erstpruefer\":\"Schindler\",\"Datum\":\"2019-02-07 09:00:00.0\",\"Semester\":\"4\",\"Zweitpruefer\":\"Hesse\",\"Modul\":\"Mikrocontroller\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3010\"}","{\"Pruefform\":\"K_90\",\"Erstpruefer\":\"Teich\",\"Datum\":\"2019-02-07 09:00:00.0\",\"Semester\":\"2\",\"Zweitpruefer\":\"Haubrock\",\"Modul\":\"Elektro- und Messtechnik\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3009\"}","{\"Pruefform\":\"M_30\",\"Erstpruefer\":\"Bekemeier\",\"Datum\":\"2019-02-06 08:00:00.0\",\"Semester\":\"6\",\"Zweitpruefer\":\"Hilbig\",\"Modul\":\"Programmierung verteilter Systeme\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3008\"}]";
            //String email2 = "[{\"ID\":\"1\",\"Name\":\"loviscach\",\"Fach\":\"SWE\",\"Datum\":\"2005-11-11\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"},{\"ID\":\"1\",\"Name\":\"wold\",\"Fach\":\"Netzwerktechnik\",\"Datum\":\"2006-11-11\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"},{\"ID\":\"3\",\"Name\":\"Ohloff\",\"Fach\":\"Mathe 3\",\"Datum\":\"2018-10-19\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"},{\"ID\":\"3\",\"Name\":\"Ohloff\",\"Fach\":\"Mathe 3\",\"Datum\":\"2018-10-19\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"},{\"ID\":\"3\",\"Name\":\"Ohloff\",\"Fach\":\"Mathe 3\",\"Datum\":\"2018-10-19\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"},{\"ID\":\"3\",\"Name\":\"Ohloff\",\"Fach\":\"Mathe 3\",\"Datum\":\"2018-10-19\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"},{\"ID\":\"3\",\"Name\":\"Ohloff\",\"Fach\":\"Mathe 3\",\"Datum\":\"2018-10-19\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"},{\"ID\":\"3\",\"Name\":\"Ohloff\",\"Fach\":\"Mathe 3\",\"Datum\":\"2018-10-19\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"},{\"ID\":\"3\",\"Name\":\"Ohloff\",\"Fach\":\"Mathe 3\",\"Datum\":\"2018-10-19\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"}]";
                String email2 =  "[{\"Pruefform\":\"K_90\",\"Erstpruefer\":\"Teich\",\"Datum\":\"2019-02-08 07:00:00.0\",\"Semester\":\"1\",\"Zweitpruefer\":\"Haubrock\",\"Modul\":\"Elektrotechnikgrundlagen\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3012\"},{\"Pruefform\":\"K_90\",\"Erstpruefer\":\"Ohlhoff\",\"Datum\":\"2019-02-07 09:00:00.0\",\"Semester\":\"3\",\"Zweitpruefer\":\"Kohlhase\",\"Modul\":\"Mathematik III\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3011\"},{\"Pruefform\":\"K_90\",\"Erstpruefer\":\"Schindler\",\"Datum\":\"2019-02-07 09:00:00.0\",\"Semester\":\"4\",\"Zweitpruefer\":\"Hesse\",\"Modul\":\"Mikrocontroller\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3010\"},{\"Pruefform\":\"K_90\",\"Erstpruefer\":\"Teich\",\"Datum\":\"2019-02-07 09:00:00.0\",\"Semester\":\"2\",\"Zweitpruefer\":\"Haubrock\",\"Modul\":\"Elektro- und Messtechnik\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3009\"},{\"Pruefform\":\"M_30\",\"Erstpruefer\":\"Bekemeier\",\"Datum\":\"2019-02-06 08:00:00.0\",\"Semester\":\"6\",\"Zweitpruefer\":\"Hilbig\",\"Modul\":\"Programmierung verteilter Systeme\",\"Studiengang\":\"Ingenieurinformatik\",\"ID\":\"3008\"}]";

                dateneinlesen.GetterSetter(email2);

                editor.clear();

                List<String> WerteZumAnzeigen = dateneinlesen.ab;
                //hinzufügen von recycleview

                // use this setting to
                // improve performance if you know that changes
                // in content do not change the layout size
                // of the RecyclerView

                // use a linear layout manager



        //Context für db connect
        final Context context = getBaseContext();
        database.database(context);

        setContentView(R.layout.activity_main);
        setContentView(R.layout.start);
        Button btngo = (Button) findViewById(R.id.btnGO);
        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(getApplicationContext(),Tabelle.class);
                startActivity(nextIntent);
                finish();


            }
        });
        Spinners spinner = new Spinners();
        List<String> spinnerArray = new ArrayList<String>();


        //anzahl der elemente
        int laenge = dateneinlesen.getlaenge();

        spinnerArray = spinner.Spinner(dateneinlesen.getStudiengang(), laenge);


        //adapter aufruf
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getBaseContext(), R.layout.simple_spinner_item, spinnerArray);


        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        spStudiengangMain = (Spinner) findViewById(R.id.spStudiengang);

        spStudiengangMain.setAdapter(adapter);

        final List<String> Studiengang = new ArrayList();

        Studiengang.add("Alle");

        spStudiengangMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                RueckgabeStudiengang.clear();
                Studiengang.add(parent.getItemAtPosition(position).toString()); //this is your selected item
                int i;
                String a;

                for (i = 0; i < dateneinlesen.getlaenge(); i++) {
                    if (Studiengang.get(Studiengang.size() - 1).toString().equals("Alle")) {
                        RueckgabeStudiengang.add(i);
                    } else {
                        if (Studiengang.get(Studiengang.size() - 1).toString().equals(dateneinlesen.studiengang[i].toString())) {
                            RueckgabeStudiengang.add(i);
                        }
                    }
//                    Toast.makeText(getApplicationContext(), RueckgabeStudiengang.get(i).toString() +"//"+ String.valueOf(i), Toast.LENGTH_SHORT).show();
                    //txtview.setText(Prof.get(Prof.size()-1).toString() + dateneinlesen.profname[i].toString());
                }
            }


            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }




    }






