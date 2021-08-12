package com.example.myapplication.API;

import com.example.myapplication.Models.ModelHero;;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface interfaceHeroes
{
    @GET("all.json")
    @Headers({"Accept: application/json"})
    Call<List<ModelHero>> getHero(
    );
}
