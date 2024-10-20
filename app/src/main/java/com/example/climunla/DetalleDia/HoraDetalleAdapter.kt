package com.example.climunla.DetalleDia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.climunla.R

class HoraDetalleAdapter(private val horaDetalleLista:List<HoraDetalle>): RecyclerView.Adapter<HoraDetalleViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoraDetalleViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        return HoraDetalleViewHolder(layoutInflater.inflate(R.layout.item_hora,parent,false))
    }

    override fun getItemCount(): Int =  horaDetalleLista.size

    override fun onBindViewHolder(holder: HoraDetalleViewHolder, position: Int) {
        val item= horaDetalleLista[position]
        holder.render(item)
    }
}