//////////////////////////////
// Spinners
//
//
//
// autor:
// inhalt: hintelgen der Daten  in Spinner-Komponenten für die Suche
// zugriffsdatum: 11.12.19
//
//
//
//
//
//
//////////////////////////////

package com.Fachhochschulebib.fhb.pruefungsplaner;

import android.app.Activity;

import java.util.List;
import java.util.ArrayList;

import android.content.Context;

public class Spinners extends Activity {

    Context context;
    //Klassenvariablen
    String[] profname = new  String[100];
    String[] ID = new String[100];
    String[] studiengang = new String[100];
    String[] semester = new String[100];
    String[] datum = new String[100];
    public List<String> Spinner(String[] variable,int laenge ) {
        List<String> spinnerArray =  new ArrayList<String>();
        List<String> spinnerArray2 = new  ArrayList<String>();
        int laenge2 = 1;
        boolean wahr = false;
        //damit alles angezeigt werden kann
        spinnerArray.add("Alle");
        for(int i = 0; i < laenge; i++) {
            wahr = false;
            for (int a = 0; a < spinnerArray.size(); a++) {
                if (spinnerArray.get(a).equals(variable[i])) {
                    wahr = true;
                }
            }
            if (!wahr)
            {
                spinnerArray.add(variable[i]);
            }
        }
        return spinnerArray;
    }

}
