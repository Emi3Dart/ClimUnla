package com.example.climunla.data.retrofit.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climunla.data.retrofit.model.CurrentResponseApi
import com.example.climunla.data.retrofit.model.ForecastResponseApi
import com.example.climunla.data.retrofit.repository.WeatherRepository
import com.example.climunla.data.retrofit.server.ApiClient
import com.example.climunla.data.retrofit.server.ApiServices
import kotlinx.coroutines.launch

class ClimaViewModel(val repository: WeatherRepository) : ViewModel() {
    constructor() : this(WeatherRepository(ApiClient().getClient().create(ApiServices::class.java)))

    suspend fun loadClimaActual(lat: Double, lon: Double, unit: String): CurrentResponseApi? {
        return try {
            repository.getClimaActual(lat, lon, unit) // Obtiene CurrentResponseApi directamente
        } catch (e: Exception) {
            null // Manejo de errores simple
        }
    }

    suspend fun loadForecastClima(lat: Double, lon: Double, unit: String): ForecastResponseApi? {
        return try {
            repository.getForecastClima(lat, lon, unit) // Obtiene ForecastResponseApi directamente
        } catch (e: Exception) {
            null // Manejo de errores simple
        }
    }
}

