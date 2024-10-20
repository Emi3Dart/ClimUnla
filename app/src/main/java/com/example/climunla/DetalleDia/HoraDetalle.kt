package com.example.climunla.DetalleDia

import java.time.LocalTime

data class HoraDetalle (
    val hora:LocalTime,
    var temperatura:String,
    var humedad:String,
    var url: String){
}