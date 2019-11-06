package com.Fachhochschulebib.fhb.pruefungsplaner;

import com.Fachhochschulebib.fhb.pruefungsplaner.model.JsonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {
    @GET(" ")
    Call<List<JsonResponse>> getJSON();
}