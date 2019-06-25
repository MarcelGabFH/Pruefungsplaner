package com.example.fhb.pruefungsplaner.model;

import android.util.Log;
import android.widget.Toast;

import com.example.fhb.pruefungsplaner.RequestInterface;
import com.example.fhb.pruefungsplaner.Terminefragment;
import com.example.fhb.pruefungsplaner.data.AppDatabase;
import com.example.fhb.pruefungsplaner.data.User;
import com.example.fhb.pruefungsplaner.model.JsonResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnect {


    public static boolean checkuebertragung = false;

    public void retro(final AppDatabase roomdaten,String Jahr, String Studiengang, String Pruefungsphase, String Termin){
        //Serveradresse
        String URLFHB = "http://thor.ad.fh-bielefeld.de:8080/";
        //URLFHB = "http://192.168.178.39:44631/";
        //uebergabe der parameter an die Adresse
        //String adresse = "PruefplanApplika/webresources/entities.pruefplaneintrag/"+Pruefungsphase+"/"+Termin+"/"+Jahr+"/";
        String adresse = "PruefplanApplika/webresources/entities.pruefplaneintrag/"+Pruefungsphase+"/"+0+"/"+Jahr+"/"+Studiengang+"/";

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
                    roomdaten.clearAllTables();
                    for (int i = 0; i < response.body().size();i++) {

                        User user = new User();

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


                            SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                            targetdatevalue = targetFormat.format(date4);

                        } catch (ParseException e) {
                            e.printStackTrace();

                        }
                        //Toast.makeText(this, date3.toString(), Toast.LENGTH_LONG).show();
                        user.setErstpruefer(response.body().get(i).getErstpruefer());
                        user.setZweitpruefer(response.body().get(i).getZweitpruefer());
                        user.setDatum(String.valueOf(targetdatevalue));
                        user.setID(response.body().get(i).getID());
                        user.setStudiengang(response.body().get(i).getStudiengang());
                        user.setModul(response.body().get(i).getModul());
                        user.setSemester(response.body().get(i).getSemester());
                        user.setPruefform(response.body().get(i).getPruefform());
                        addUser(roomdaten, user);
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
