package com.example.fhb.pruefungsplaner;


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
import android.widget.TextView;
import android.widget.Toast;

import com.example.fhb.pruefungsplaner.data.AppDatabase;
import com.example.fhb.pruefungsplaner.data.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class Optionen extends Fragment {
    private boolean speicher;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences.Editor mEditorAdresse;
    private JSONArray response;
    static EditText txtAdresse;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.optionfragment, container, false);


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

        txtAdresse.addTextChangedListener(new TextWatcher() {


            @Override
            public void afterTextChanged(Editable s) {

                mEditorAdresse.clear();
                mEditorAdresse.apply();
                mEditorAdresse.putString("Server-Adresse2", txtAdresse.getText().toString());
                mEditorAdresse.apply();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        btnDb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AppDatabase database2 = AppDatabase.getAppDatabase(v.getContext());
                Log.d("Test", "interne Db löschen");
                database2.clearAllTables();

                Toast.makeText(v.getContext(), "Datenbank gelöscht", Toast.LENGTH_SHORT).show();

            }
        });

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


    public void insertfav() {


    }


}

