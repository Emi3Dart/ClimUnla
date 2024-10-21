package com.example.climunla.data.retrofit.viewModel

import androidx.lifecycle.ViewModel
import com.example.climunla.data.retrofit.repository.WeatherRepository
import com.example.climunla.data.retrofit.server.ApiClient
import com.example.climunla.data.retrofit.server.ApiServices
import retrofit2.create

class ClimaViewModel(val repository: WeatherRepository):ViewModel() {

    constructor():this(WeatherRepository(ApiClient().getClient().create(ApiServices::class.java)))

    fun loadClimaActual(lat:Double,lon:Double,unit:String)=
        repository.getClimaActual(lat,lon,unit)

    fun loadForecastClima(lat:Double,lon:Double,unit:String)=
        repository.getForecastClima(lat,lon,unit)

}