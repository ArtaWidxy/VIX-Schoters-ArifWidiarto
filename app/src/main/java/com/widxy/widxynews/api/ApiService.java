package com.widxy.widxynews.api;

import com.widxy.widxynews.entity.ResponNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/v2/top-headlines")
    Call<ResponNews> getListNews(@Query("country") String country, @Query("category") String category, @Query("apiKey") String apiKey);

    @GET("/v2/top-headlines")
    Call<ResponNews> getListAllNews(@Query("country") String country, @Query("apiKey") String apiKey);
}
