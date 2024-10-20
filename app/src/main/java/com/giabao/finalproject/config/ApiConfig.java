package com.giabao.finalproject.config;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {
    public static final String baseURL = "https://6713ccb7690bf212c75fcf14.mockapi.io/";
    private static Retrofit client;


    // Eager build with null check
    public static Retrofit getRetrofitClient() {
        if (client == null) {
            client = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return client;
    }

}
