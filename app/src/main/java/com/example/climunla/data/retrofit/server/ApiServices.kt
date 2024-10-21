package com.example.climunla.data.retrofit.server


import com.example.climunla.data.retrofit.model.CityResponseApi
import com.example.climunla.data.retrofit.model.CurrentResponseApi
import com.example.climunla.data.retrofit.model.ForecastResponseApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("data/2.5/weather")
    fun getClimaActual(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("lang") lang:String,
        @Query("units") units:String,
        @Query("appid") ApiKey: String,
    ):Call<CurrentResponseApi>

    @GET("data/2.5/forecast")
    fun getForecastClima(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("units") units:String,
        @Query("appid") ApiKey: String,
    ):Call<ForecastResponseApi>

    @GET("geo/1.0/direct")
    fun getCitiesList(
        @Query("q") q:String,
        @Query("limit") limit:Int,
        @Query("appid") ApiKey: String
    ):Call<CityResponseApi>

}