package com.example.fhb.pruefungsplaner;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import com.android.volley.toolbox.Volley;
import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public final class dbconnect extends Activity {

        // variable to hold context
        private Context context;


    public void database(Context a,String Jahr,String Studiengang,String Pruefungsphase,String Termin) {


        //init shared pref und editor
        final SharedPreferences pref = a.getSharedPreferences("JSON", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();





        //volley init
        RequestQueue queue = Volley.newRequestQueue(a);

        //Datenbank adresse
        String url = "http://thor.ad.fh-bielefeld.de:8080/PruefplanApplika/webresources/entities.pruefplaneintrag/"+Pruefungsphase+"/"+Termin+"/"+Jahr+"/"+Studiengang+"/";
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
                String speicher = ("error in dbconnect");
                editor.putString("JSON", speicher); // Storing string
                //editor bestätigen
                editor.commit();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(a);
        requestQueue.add(jsonArrayRequest);
/*
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,

                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {


                        // save the characters of the response string.
                        //String speicher = (response.substring(0, response.length()));

                        //String[] speicher2 = speicher.split("<br>");
                        //String[] speicher3 = speicher2[1].toString().split("</body>");
                        //init Shared Pref
                        editor.putString("JSON",response); // Storing string
                        //editor neuladen/bestätigen
                        editor.commit();


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                String speicher = ("error in dbconnect");
                //String speicher  ="[{\"ID\":\"1\",\"Name\":\"loviscach\",\"Fach\":\"SWE\",\"Datum\":\"2005-11-11\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"},{\"ID\":\"1\",\"Name\":\"gurenwold\",\"Fach\":\"Netzwerktechnik\",\"Datum\":\"2006-11-11\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"},{\"ID\":\"3\",\"Name\":\"Ohloff\",\"Fach\":\"Mathe 3\",\"Datum\":\"2018-10-19\",\"Studiengang\":\"Ingenieurinformatik\",\"Semester\":\"3\"}]";

                //init shared pref
                editor.putString("JSON", speicher); // Storing string
                //editor bestätigen
                editor.commit();
            }

        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
*/
    }

    }

