package com.example.climunla.data.retrofit.repository

import com.example.climunla.data.retrofit.server.ApiServices

class WeatherRepository(val api:ApiServices) {

    fun getClimaActual(lat:Double,lon:Double,unit:String) =
        api.getClimaActual(lat,lon,"es",unit,"cf5f4136b70017608389ac31e46e8f0d")

    fun getForecastClima(lat:Double,lon:Double,unit:String) =
        api.getForecastClima(lat,lon,unit,"cf5f4136b70017608389ac31e46e8f0d")
}