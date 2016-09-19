package com.psychscribe.rest;

import com.psychscribe.base.PsychScribeApp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRequest {

    public static final String API_BASE_URL = "http://dev-imaginovation.net/rojas/api/";   //dev-imaginovation.net/rojas/rojas-apilist/#/

    private static Retrofit provideRestAdapter() {
        return new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(PsychScribeApp.getInstance().getOkHttpClient())
                .build();
    }

    public static ResponseInterface provideInterface() {
        return provideRestAdapter().create(ResponseInterface.class);
    }
}