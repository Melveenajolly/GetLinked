package com.getlinked;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebClient {

    //this should be the same as the server.address in the application.properies file in webapp (server)
    private static final String BASE_URL = "http://192.168.0.100:8080/";

    ApiInterface apiService;

    public WebClient() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logger)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //connecting with ApiInterface
        apiService = retrofit.create(ApiInterface.class);

    }

    public ApiInterface getService() {
        return apiService;
    }

}