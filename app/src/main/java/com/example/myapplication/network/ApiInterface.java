package com.example.myapplication.network;

import com.example.myapplication.model.TravelResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*
** Uses the URL Endpoint and other queries to complete the call.
**/
public interface ApiInterface {

    // fetch details.
    @GET(".")
    Call<TravelResponse> getTravelDetail();



}
