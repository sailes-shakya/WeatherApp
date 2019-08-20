package com.sailesh.weatherapp.Network;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator {



    private static final long TIME_OUT = 15 * 1000;
    public static Retrofit retrofit;
    public static Retrofit normalRetrofit;


    public static final String API_BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static OkHttpClient.Builder normalHttpClient = new OkHttpClient.Builder();


    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .client(getBuilder().build())
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit.Builder normalBuilder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .client(getBuilder().build())
                    .addConverterFactory(GsonConverterFactory.create());

    public static OkHttpClient.Builder getBuilder() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);
        return client;
    }



    public static <S> S createNormalService(Class<S> serviceClass, final Context context) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        normalHttpClient.addInterceptor(interceptor).build();
        normalHttpClient.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        normalHttpClient.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        normalHttpClient.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        normalRetrofit = normalBuilder.client(normalHttpClient.build()).build();
        return normalRetrofit.create(serviceClass);
    }

}
