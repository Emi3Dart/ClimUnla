package com.example.climunla.DetalleDia

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.climunla.R

class HoraDetalleViewHolder (view: View):RecyclerView.ViewHolder(view){
    val hora =view.findViewById<TextView>(R.id.tvTime)
    val temperatura =view.findViewById<TextView>(R.id.tvTemperature)
    val humedad=view.findViewById<TextView>(R.id.tvHumidity)
    val foto =view.findViewById<ImageView>(R.id.ivWeatherIcon)

    fun render(detalle: HoraDetalle){
        hora.text= detalle.hora.toString()
        temperatura.text=detalle.temperatura
        humedad.text=detalle.humedad

    }
}