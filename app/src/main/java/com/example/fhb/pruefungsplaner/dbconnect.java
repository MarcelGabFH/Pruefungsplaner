package com.example.fhb.pruefungsplaner;
//////////////////////////////
// dbconnect
//
//
//
// autor:
// inhalt:  Verbindung mit der Datenbank aufbauen und Json Daten lesen/bereitstellen
// zugriffsdatum: 02.05.19
//
//
//
//
//
//
//////////////////////////////

import android.app.Activity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import android.content.Intent;
import android.content.SharedPreferences;

import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.VolleyLog.TAG;

public final class dbconnect extends AppCompatActivity {

        // variable to hold context

        private Context context;
        private  String URLFHB;
        private  String adresse;
        private boolean warteaufresponse;

    public int database(final Context a, String Jahr, String Studiengang, String Pruefungsphase, String Termin, final Pruefplaneintrag pruefplan) {
        //Serveradresse
        URLFHB = "http://thor.ad.fh-bielefeld.de:8080/";


        //URLFHB = "http://192.168.178.39:44631/";
        //uebergabe der parameter an die Adresse
        adresse = "PruefplanApplika/webresources/entities.pruefplaneintrag/"+Pruefungsphase+"/"+Termin+"/"+Jahr+"/"+Studiengang+"/";
        //adresse = "PruefplanApplika/webresources/entities.pruefplaneintrag/"+Pruefungsphase+"/"+0+"/"+Jahr+"/"+Studiengang+"/";

        //init shared pref und editor
        final SharedPreferences pref = a.getSharedPreferences("JSON", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();

        //volley init
        //Datenbank adresse
        RequestQueue requestQueue = Volley.newRequestQueue(a);
        warteaufresponse = false;
        String url = URLFHB+adresse;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                editor.clear().apply();

                //for (int i = 0; i < response.length(); i++) {
                    try {
                        response.getJSONObject(0);
                        Log.d(TAG, response.toString());
                        //JSONArray jsonarray = new JSONArray();
                        //jsonarray.put(i,jsonObject);
                        pruefplan.Pruefdaten(response.toString());


                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                //}
                warteaufresponse = true;

                editor.putString("JSON",response.toString()); // Storing string
                //editor neuladen/bestätigen
                editor.commit();




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //init shared pref
                String verbindugsfehler = ("error in dbconnect");
                editor.putString("JSON", verbindugsfehler); // Storing string
                //editor bestätigen
                editor.commit();

            }
        });



        requestQueue.add(jsonArrayRequest);
      return 1;
    }

    public boolean warteresponse() {
        return this.warteaufresponse;
    }

    }

