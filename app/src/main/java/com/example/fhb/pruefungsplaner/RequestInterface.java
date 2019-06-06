package com.example.fhb.pruefungsplaner;

import com.example.fhb.pruefungsplaner.model.JsonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {
    @GET("1")
    Call<List<JsonResponse>> getJSON();
}