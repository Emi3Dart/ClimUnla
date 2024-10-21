package com.example.climunla.data.retrofit.server

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    // OkHttpClient: Crea un cliente HTTP con **timeout de 60 segundos** para conexiones y lecturas.
    private lateinit var retrofit: Retrofit
    private val client=OkHttpClient.Builder()
        .connectTimeout(60,TimeUnit.SECONDS)
        .readTimeout(60,TimeUnit.SECONDS)
        .readTimeout(60,TimeUnit.SECONDS)
        .build()



    // getClient(): Retorna una instancia de **Retrofit** configurada para hacer llamadas a la API.
    fun getClient():Retrofit{
        retrofit= Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")// URL base de la API**
            .client(client)// Usa el cliente OkHttp configurado arriba.
            .addConverterFactory(GsonConverterFactory.create())// Convierte el JSON** en objetos usando Gson
            .build()
        return retrofit
    }
}