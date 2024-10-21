package com.example.climunla.data.retrofit.adapter

import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.climunla.data.retrofit.model.ForecastResponseApi
import com.example.climunla.databinding.ActivityMainBinding
import com.example.climunla.databinding.ForecastViewholderBinding
import java.text.SimpleDateFormat

class ForecastAdapter :RecyclerView.Adapter<ForecastAdapter.ViewHolder>(){
    private lateinit var binding: ForecastViewholderBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding=ForecastViewholderBinding.inflate(inflater,parent,false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ForecastAdapter.ViewHolder, position: Int) {
        val binding = ForecastViewholderBinding.bind(holder.itemView)
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(differ.currentList[position].dtTxt.toString())
        val calendar = Calendar.getInstance()
        calendar.time = date

        val dayOfWeekName = when(calendar.get(Calendar.DAY_OF_WEEK)){
            1->"Lun"
            2->"Mar"
            3->"Mie"
            4->"Jue"
            5->"Vie"
            6->"Sab"
            7->"Dom"
            else -> "-"
        }
        binding.tvNombreDia.text = dayOfWeekName
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val amPm = if(hour<12)"am" else "pm"
        val hour12 = calendar.get(Calendar.HOUR)
        binding.tvHora.text = hour12.toString()+amPm
        binding.tvTemperaturaDia.text = differ.currentList[position].main?.temp?.let { Math.round(it) }.toString()+"°"

        val icon = when(differ.currentList[position].weather?.get(0)?.icon.toString()){
            "01d","01n" ->"sunny"
            "02d","02n" ->"cloudy_sunny"
            "03d","03n" ->"cloudy_sunny"
            "04d","04n" ->"cloudy"
            "09d","09n" ->"rainy"
            "10d","10n" ->"rainy"
            "11d","11n" ->"storm"
            "13d","13n" ->"snowy"
            "50d","50n" ->"windy"
            else -> "sunny"
        }
        val drawableResourceId: Int = binding.root.resources.getIdentifier(
            icon,
            "drawable",binding.root.context.packageName
        )

        Glide.with(binding.root.context)
            .load(drawableResourceId)
            .into(binding.ivClimaHorario)


    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount()=differ.currentList.size


    private val differCallback = object : DiffUtil.ItemCallback<ForecastResponseApi.data>(){
        override fun areItemsTheSame(
            oldItem: ForecastResponseApi.data,
            newItem: ForecastResponseApi.data
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ForecastResponseApi.data,
            newItem: ForecastResponseApi.data
        ): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallback)
}