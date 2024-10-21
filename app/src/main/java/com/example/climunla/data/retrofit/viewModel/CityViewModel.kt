package com.example.climunla.data.retrofit.viewModel

import androidx.lifecycle.ViewModel
import com.example.climunla.data.retrofit.repository.CityRepository
import com.example.climunla.data.retrofit.server.ApiClient
import com.example.climunla.data.retrofit.server.ApiServices
import retrofit2.create

class CityViewModel(val repository: CityRepository) : ViewModel() {
    constructor():this(CityRepository(ApiClient().getClient().create(ApiServices::class.java)))
    fun loadCity(q:String,limit:Int) =
        repository.getCities(q,limit)
}