package com.getlinked;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapsApiInterface {
    @GET("api/geocode/json")
    Call<JsonObject> GetMyLocationFromGoogle(@Query("address") String address,
                                             @Query("key") String key);
}
