package com.example.budgee;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.budgee.OpenRouterApiService;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class RetrofitClient {

    private static final String BASE_URL = "https://openrouter.ai/api/v1/";
    public static OpenRouterApiService apiService;

    public static OpenRouterApiService getApiService() {
        if (apiService == null) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            apiService = retrofit.create(OpenRouterApiService.class);
        }
        return apiService;
    }
}