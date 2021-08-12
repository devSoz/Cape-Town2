package com.example.myapplication.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIHeroes
{
    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()

                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl("https://akabab.github.io/superhero-api/api/")
                    .build();
        }
        return retrofit;
    }
}
