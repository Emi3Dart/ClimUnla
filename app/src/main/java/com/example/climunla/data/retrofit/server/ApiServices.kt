package com.example.climunla.data.retrofit.server


import com.example.climunla.data.retrofit.model.CurrentResponseApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("data/2.5/weather")
    fun getClimaActual(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("units") units:String,
        @Query("appid") ApiKey: String,
    ):Call<CurrentResponseApi>
}