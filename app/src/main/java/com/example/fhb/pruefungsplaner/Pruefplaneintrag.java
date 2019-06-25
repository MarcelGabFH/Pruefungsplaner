package com.example.fhb.pruefungsplaner;

//////////////////////////////
// Prüfplaneintrag
//
//
//
// autor:
// inhalt: aufteilen des json string in variablen damit von anderen Klassen drauf zu gegriffen werden kann
// zugriffsdatum: 02.05.19
//
//
//
//
//
//
//////////////////////////////


import android.app.Activity;
import android.widget.Toast;

import org.json.JSONArray;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public final class Pruefplaneintrag extends Activity {
    //Klassenvariablen
    String[] profname = new String[100];
    String[] profnamezwei = new String[100];
    String[] ID = new String[100];
    String[] studiengang = new String[100];
    String[] semester = new String[100];
    String[] datum = new String[100];
    String[] Fach = new String[100];
    List<String> ab = new ArrayList<String>();
    int laenge;

    //constructor JSONarray aus der wird in die Klassenvariablen abgelegt
    final void Pruefdaten(String ausgewaehltePruefungen) {
        //Try abfrage für fehlerausgabe
        try {
            //JSONAarray mit den Daten aus der Datenbank
            JSONArray json = new JSONArray(ausgewaehltePruefungen);
            //Zuweisung der Elemente des Arrays in die Klassenvariablen
            for (int i = 0; i < json.length(); i++) {
                this.Fach[i] = json.getJSONObject(i).getString("Modul");
                this.profname[i] = json.getJSONObject(i).getString("Erstpruefer");
                this.profnamezwei[i] = json.getJSONObject(i).getString("Zweitpruefer");
                this.semester[i] = json.getJSONObject(i).getString("Semester");
                this.studiengang[i] = json.getJSONObject(i).getString("Studiengang");
                this.ID[i] = json.getJSONObject(i).getString("ID");

                Date sourceDate = null;
                String date3;
                String date2 = json.getJSONObject(i).getString("Datum");


                date3 = date2.replaceFirst("CET", "");
                date3 = date3.replaceFirst("CEST","");


                String targetdatevalue;
                targetdatevalue = null;
                try {
                    DateFormat dateFormat = new SimpleDateFormat(
                            "EEE MMM dd HH:mm:ss yyyy", Locale.US);
                    Date date4 = dateFormat.parse(date3);


                    SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                    targetdatevalue = targetFormat.format(date4);

                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(this, date3.toString(), Toast.LENGTH_LONG).show();

                this.datum[i] = targetdatevalue;
                //this.datum[i] = json.getJSONObject(i).getString("Datum");
            }
            //anfangsinitialisierung von den startwerten
            if (ab.size() < 1) {
                this.laenge = json.length();
                for (int a = 0; a < this.laenge; a++) {
                    this.ab.add(String.valueOf(a));
                }
            }
        }
        //fehlerausgabe
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    //getter methoden
    public int getlaenge() {
        return laenge;
    }

    public String[] getFach() {
        return Fach;
    }

    public String[] getID() {
        return ID;
    }

    public String[] getStudiengang() {
        return studiengang;
    }

    public String[] getDatum() {
        return datum;
    }

    public String[] getProfname() {
        return profname;
    }

    public String[] getProfname2() {
        return profnamezwei;
    }

    public String[] getSemester() {
        return semester;
    }

    public List<String> getab() {
        return ab;
    }

    final public void ab(List<String> ab) {
        this.ab = ab;
    }
}
