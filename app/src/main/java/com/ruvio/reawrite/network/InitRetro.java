package com.ruvio.reawrite.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class InitRetro {
    public static String API_URL = "https://reawrite.000webhostapp.com/";
    private  static Retrofit retrofit = null;

    public static Retrofit InitApi() {
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }






}