package com.Fachhochschulebib.fhb.pruefungsplaner.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.Fachhochschulebib.fhb.pruefungsplaner.Optionen;
import com.Fachhochschulebib.fhb.pruefungsplaner.RequestInterface;
import com.Fachhochschulebib.fhb.pruefungsplaner.data.AppDatabase;
import com.Fachhochschulebib.fhb.pruefungsplaner.data.User;

import org.json.JSONArray;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitConnect {
    public  String Jahr = null,Studiengang= null,Pruefungsphase= null,Termin = null,Termine;
    public static boolean checkuebertragung = false;
    private boolean checkvalidate = false;
    Context ctx2;

    public void retro(Context ctx, final AppDatabase roomdaten, final String Jahr, final String Studiengang, final String Pruefungsphase, final String Termin){
        //Serveradresse
        SharedPreferences mSharedPreferencesAdresse = ctx.getSharedPreferences("Server-Adresse", 0);


         ctx2 = ctx;
         Termine = Termin;
        //Creating editor to store values to shared preferences
        String URLFHB = mSharedPreferencesAdresse.getString("Server-Adresse2","http://thor.ad.fh-bielefeld.de:8080/");

        Log.d("Output Studiengang",Pruefungsphase.toString());
        Log.d("Output Studiengang",Termin.toString());
        Log.d("Output Studiengang",Jahr.toString());




        //String URLFHB = "http://thor.ad.fh-bielefeld.de:8080/";
        //String URLFHB = "http://192.168.178.39:44631/";
        //uebergabe der parameter an die Adresse
        //String adresse = "PruefplanApplika/webresources/entities.pruefplaneintrag/"+Pruefungsphase+"/"+Termin+"/"+Jahr+"/";
        String adresse = "PruefplanApplika/webresources/entities.pruefplaneintrag/"+Pruefungsphase+"/"+Termin+"/"+Jahr+"/"+Studiengang+"/";

        String URL = URLFHB+adresse;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RequestInterface request = retrofit.create(RequestInterface.class);
        Call<List<JsonResponse>> call = request.getJSON();
        call.enqueue(new Callback<List<JsonResponse>>() {
            @Override
            public void onResponse(Call<List<JsonResponse>> call, Response<List<JsonResponse>> response) {
                response.body();
                if (response.isSuccessful()) {
                    List<User> userdaten = roomdaten.userDao().getAll2();
                    //roomdaten.clearAllTables();

                    String validation = Jahr+Studiengang+Pruefungsphase;

                    String checkTermin = "0";
                    for(int j = 0; j < userdaten.size();j++) {
                        if (userdaten.get(j).getValidation().equals(validation)){
                            System.out.println("aufgerufen2223");
                            checkTermin = userdaten.get(j).getTermin().toString();
                            checkvalidate = true;
                        }}

                    //Schleife um jedes erhaltene Prüfungsobjekt in die lokale Datenbank hinzuzufügen
                    for (int i = response.body().size()-1 ; i > 0; --i) {

                        //User ist die modelklasse für die angekommenden Prüfungsobjekte
                        User user = new User();

                        //Festlegen vom Dateformat
                        String date3;
                        String date2 = response.body().get(i).getDatum();
                        date3 = date2.replaceFirst("CET", "");
                        date3 = date3.replaceFirst("CEST","");
                        String targetdatevalue;
                        targetdatevalue = null;
                        try {
                            DateFormat dateFormat = new SimpleDateFormat(
                                    "EEE MMM dd HH:mm:ss yyyy", Locale.US);
                            Date date4 = dateFormat.parse(date3);
                            SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            targetdatevalue = targetFormat.format(date4);
                            String[] stdate = targetdatevalue.split("-");
                            Date c = Calendar.getInstance().getTime();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String formattedDate = df.format(c);
                            String[] stdate2 = formattedDate.split("-");
                            String st= stdate[1].replace("0", "");

                            //Überprüfung System out
                            System.out.println("Current time => " + stdate2[1]);
                            System.out.println("Current time => " + st);
                            System.out.println("Current time => " + Termine);

                            //überprüfung erste Prüfungsphase oder zweite
                            if(Integer.valueOf(st) > Integer.valueOf(stdate2[1]))
                            {
                                if (Termine.equals("0")) {
                                    if(!checkTermin.equals("1")) {
                                        //roomdaten.clearAllTables();
                                    }
                                    retro(ctx2, roomdaten, Jahr, Studiengang, Pruefungsphase, "1");
                                    System.out.println("aufgerufen");
                                    break;
                                }
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();

                        }
                        //Toast.makeText(this, date3.toString(), Toast.LENGTH_LONG).show();


                         if(!checkvalidate){
                             System.out.println("aufgerufen222");
                             //erhaltene Werte zur Datenbank hinzufügen
                            user.setErstpruefer(response.body().get(i).getErstpruefer());
                            user.setZweitpruefer(response.body().get(i).getZweitpruefer());
                            user.setDatum(String.valueOf(targetdatevalue));
                            user.setID(response.body().get(i).getID());
                            user.setStudiengang(response.body().get(i).getStudiengang());
                            user.setModul(response.body().get(i).getModul());
                            user.setSemester(response.body().get(i).getSemester());
                            user.setTermin(response.body().get(i).getTermin());

                            //lokale datenbank initialiseren
                             AppDatabase database2 = AppDatabase.getAppDatabase(ctx2);
                             List<User> userdaten2 = database2.userDao().getAll2();
                             Log.d("Test4", String.valueOf(userdaten2.size()));

                             try {
                                 for (int b = 0; b < Optionen.ID.size(); b++) {
                                     if (user.getID().equals(Optionen.ID.get(b))) {

                                         Log.d("Test4", String.valueOf(userdaten2.get(b).getID()));
                                         user.setFavorit(true);
                                     }
                                 }
                             }
                             catch (Exception e)
                             {


                             }

                            //Überprüfung von Klausur oder Mündliche Prüfung
                            String Klausur = "K_90";
                            if(Klausur.equals(response.body().get(i).getPruefform())) {
                                user.setPruefform("Klausur");
                            }else{
                                user.setPruefform("Mündliche Prüfung");

                            }

                            //Schlüssel für die Erkennung bzw unterscheidung Festlegen
                            user.setValidation(Jahr + Studiengang + Pruefungsphase);
                             System.out.println(Jahr + Studiengang + Pruefungsphase);
                             addUser(roomdaten, user);

                        }
                    }
                    checkuebertragung = true;

                }
                else { System.out.println(" :::. NO RESPONSE .::: "); }
            }



            @Override
            public void onFailure(Call<List<JsonResponse>> call, Throwable t) {
                Log.d("Error",t.getMessage());

            }
        });
    }

    public static User addUser(final AppDatabase db, User user) {
        db.userDao().insertAll(user);
        System.out.println(user.getErstpruefer());
        System.out.println("\n");
        return user;
    }




    }
