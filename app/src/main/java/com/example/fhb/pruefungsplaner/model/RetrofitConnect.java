package com.example.fhb.pruefungsplaner.model;

import android.util.Log;
import android.widget.Toast;

import com.example.fhb.pruefungsplaner.RequestInterface;
import com.example.fhb.pruefungsplaner.model.JsonResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnect {


    public void retro(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://thor.ad.fh-bielefeld.de:8080/PruefplanApplika/webresources/entities.pruefplaneintrag/W/0/2018/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<List<JsonResponse>> call = request.getJSON();
        call.enqueue(new Callback<List<JsonResponse>>() {
            @Override
            public void onResponse(Call<List<JsonResponse>> call, Response<List<JsonResponse>> response) {
                response.body();
                if (response.isSuccessful()) {

                    for (int i = 0; i < response.body().size();i++) {
                        System.out.println(" Href ::::. : " + response.body().get(i).getErstpruefer()+ response.body().get(i).getDatum()+response.body().get(i).getID()+response.body().get(i).getStudiengang()+response.body().get(i).getModul());
                    }

                }
                else { System.out.println(" :::. NO RESPONSE .::: "); }
            }



            @Override
            public void onFailure(Call<List<JsonResponse>> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });


    }
    }
