package com.example.fhb.pruefungsplaner;


import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;


public final class GetterSetter extends Activity {
    //Klassenvariablen
    String[] profname = new  String[100];
    String[] profnamezwei = new  String[100];
    String[] ID = new String[100];
    String[] studiengang = new String[100];
    String[] semester = new String[100];
    String[] datum = new String[100];
    String[] Fach = new String[100];
    List<String> ab = new ArrayList<String>();
    int laenge;
    //constructor JSONarray aus der wird in die Klassenvariablen abgelegt
    final public String[] GetterSetter(String beispiel)
    {
        //Try abfrage für fehlerausgabe
        try {
            //JSONAarray mit den Daten aus der Datenbank
            JSONArray json = new JSONArray(beispiel);
            //Zuweisung der Elemente des Arrays in die Klassenvariablen
            for (int i=0; i< json.length(); i++) {
                this.Fach[i] = json.getJSONObject(i).getString("Modul");
                this.profname[i] = json.getJSONObject(i).getString("Erstpruefer");
                this.profnamezwei[i] = json.getJSONObject(i).getString("Zweitpruefer");
                this.semester[i] = json.getJSONObject(i).getString("Semester");
                this.studiengang[i] = json.getJSONObject(i).getString("Studiengang");
                this.ID[i] = json.getJSONObject(i).getString("ID");
                this.datum[i] = json.getJSONObject(i).getString("Datum");
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
        catch(Exception e){
            e.printStackTrace();
        }
        //return beispiel
        return (datum);
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
        return  studiengang;
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
    final public void ab( List<String> ab) {
        this.ab = ab;
    }
}
