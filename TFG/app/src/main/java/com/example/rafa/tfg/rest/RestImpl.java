package com.example.rafa.tfg.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rafa on 23/01/2018.
 */

public class RestImpl {

    public static RestInterface getRestInstance(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.102:8000").addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RestInterface rest = retrofit.create(RestInterface.class);
        return rest;
    }
}
