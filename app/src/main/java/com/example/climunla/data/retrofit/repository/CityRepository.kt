package com.example.climunla.data.retrofit.repository

import com.example.climunla.data.retrofit.server.ApiServices

class CityRepository(val api: ApiServices) {
    fun getCities(q:String, limit:Int)=
        api.getCitiesList(q,limit,"cf5f4136b70017608389ac31e46e8f0d")
}