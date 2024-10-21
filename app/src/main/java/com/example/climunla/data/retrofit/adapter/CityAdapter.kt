package com.example.climunla.data.retrofit.adapter

import android.content.Intent
import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.climunla.MainActivity
import com.example.climunla.data.retrofit.model.CityResponseApi
import com.example.climunla.data.retrofit.model.ForecastResponseApi
import com.example.climunla.databinding.ActivityMainBinding
import com.example.climunla.databinding.CityViewholderBinding
import com.example.climunla.databinding.ForecastViewholderBinding
import java.text.SimpleDateFormat

class CityAdapter :RecyclerView.Adapter<CityAdapter.ViewHolder>(){
    private lateinit var binding: CityViewholderBinding

    //Infla el diseño de la vista para cada ciudad utilizando CityViewholderBinding.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding=CityViewholderBinding.inflate(inflater,parent,false)
        return ViewHolder()
    }
    //Asigna el nombre de la ciudad (differ.currentList[position].name) al TextView correspondiente (tvCiudadNombre).
    override fun onBindViewHolder(holder: CityAdapter.ViewHolder, position: Int) {
        val binding = CityViewholderBinding.bind(holder.itemView)
        binding.tvCiudadNombre.text=differ.currentList[position].name
        //Establece un OnClickListener para la raíz de la vista (binding.root), que al ser clicada, crea un
        // Intent para iniciar la MainActivity, pasando las coordenadas de latitud y longitud (lat y lon) de la ciudad seleccionada,
        // así como su nombre
        binding.root.setOnClickListener {
            val intent = Intent(binding.root.context,MainActivity::class.java)
            intent.putExtra("lat",differ.currentList[position].lat)
            intent.putExtra("lon",differ.currentList[position].lon)
            intent.putExtra("name",differ.currentList[position].name)
            binding.root.context.startActivity(intent)
        }
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount()=differ.currentList.size


    private val differCallback = object : DiffUtil.ItemCallback<CityResponseApi.CityResponseApiItem>(){
        //Las funciones areItemsTheSame y areContentsTheSame se usan para comparar elementos y evitar
        // actualizaciones innecesarias si los datos no han cambiado.
        override fun areItemsTheSame(
            oldItem: CityResponseApi.CityResponseApiItem,
            newItem: CityResponseApi.CityResponseApiItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CityResponseApi.CityResponseApiItem,
            newItem: CityResponseApi.CityResponseApiItem
        ): Boolean {
            return oldItem == newItem
        }

    }
    //Al igual que en ForecastAdapter, se utiliza AsyncListDiffer para manejar
    // las diferencias entre la lista actual de ciudades. Esto mejora el rendimiento del RecyclerView,
    // ya que solo se actualizan los elementos que cambian
    val differ = AsyncListDiffer(this,differCallback)
}