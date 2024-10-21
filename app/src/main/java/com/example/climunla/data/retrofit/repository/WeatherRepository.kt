package com.example.climunla.data.retrofit.repository

import com.example.climunla.data.retrofit.server.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(val api: ApiServices) {

    suspend fun getClimaActual(lat: Double, lon: Double, unit: String) =
        withContext(Dispatchers.IO) {
            api.getClimaActual(lat, lon, "es", unit, "cf5f4136b70017608389ac31e46e8f0d") // Devuelve CurrentResponseApi directamente
        }

    suspend fun getForecastClima(lat: Double, lon: Double, unit: String) =
        withContext(Dispatchers.IO) {
            api.getForecastClima(lat, lon, unit, "cf5f4136b70017608389ac31e46e8f0d") // Devuelve ForecastResponseApi directamente
        }
}
