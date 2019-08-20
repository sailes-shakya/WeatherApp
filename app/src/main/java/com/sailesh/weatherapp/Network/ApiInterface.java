package com.sailesh.weatherapp.Network;

import com.sailesh.weatherapp.Model.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("forecast?daily")
    Call<WeatherModel> getContact(@Query("lat") String lat,@Query("lon") String lon,@Query("cnt") String cnt,@Query("APPID") String appid,@Query("units") String un);
}
