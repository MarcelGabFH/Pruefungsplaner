package com.Fachhochschulebib.fhb.pruefungsplaner;
//////////////////////////////
// Loginhandler
//
//
//
// autor:
// inhalt:  übeprüfen und bearbeiten des loginprozess
// zugriffsdatum: 15.05.19
//
//
//
//
//
//
//////////////////////////////


import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.IOException;

public class Loginhandler {

    private String user = null;
    private String passwort = null;
    private String URL2 = ("http://192.168.178.39:44631/PruefplanApplika/login.jsp?username=");
    public String total = null;
    private Context context;

    protected boolean checkUsername(String user,Context context) {

        this.context = context;
        this.user = user;
        if (user.length() < 2) {
        } else { return false; }
        return true;
    }


    public boolean checkPasswort(String passwort) throws IOException {
        this.passwort = passwort;
        if (passwort.length() < 2) {

        } else {
            if (connectToURL()) {return false;}
        }

        return true;
    }

   private boolean connectToURL() throws IOException {

        String    url = (URL2 + this.user + "&passwort=" + this.passwort);
         //total = url;
       RequestQueue queue = Volley.newRequestQueue(context);

       // Request a string response from the provided URL.
       StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       // Display the first 500 characters of the response string.
                       //textView.setText("Response is: "+ response.substring(0,500));
                       total = response.substring(response.length()-35,response.length()-18);
                   }
               }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               //textView.setText("That didn't work!");
               total = "error";
           }
       });

        // Add the request to the RequestQueue.
       queue.add(stringRequest);



    return true;
    }

}
