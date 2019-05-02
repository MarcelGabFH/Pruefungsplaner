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
import android.content.SharedPreferences;
import com.android.volley.toolbox.Volley;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class dbconnect extends Activity {

        // variable to hold context
        private Context context;
        private  String URLFHB;
        private  String adresse;

    public int database(Context a,String Jahr,String Studiengang,String Pruefungsphase,String Termin) {
        //Serveradresse
        URLFHB = "http://thor.ad.fh-bielefeld.de:8080/";
        //uebergabe der parameter an die Adresse
        adresse = "PruefplanApplika/webresources/entities.pruefplaneintrag/"+Pruefungsphase+"/"+Termin+"/"+Jahr+"/"+Studiengang+"/";

        //init shared pref und editor
        final SharedPreferences pref = a.getSharedPreferences("JSON", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();

        //volley init
        //Datenbank adresse
        String url = URLFHB+adresse;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                editor.clear().apply();

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        //JSONArray jsonarray = new JSONArray();
                        //jsonarray.put(i,jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
                editor.putString("JSON",response.toString()); // Storing string
                //editor neuladen/bestätigen
                editor.apply();

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
        RequestQueue requestQueue = Volley.newRequestQueue(a);
        requestQueue.add(jsonArrayRequest);

    return 1;
    }

    }

