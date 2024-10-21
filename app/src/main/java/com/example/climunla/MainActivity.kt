package com.example.climunla

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climunla.data.retrofit.adapter.ForecastAdapter
import com.example.climunla.data.retrofit.model.CurrentResponseApi
import com.example.climunla.data.retrofit.model.ForecastResponseApi
import com.example.climunla.data.retrofit.viewModel.ClimaViewModel
import com.example.climunla.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var binding: ActivityMainBinding
    private val climaViewModel: ClimaViewModel by viewModels()
    private val forecastAdapter by lazy { ForecastAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.titulo)

        val lat = -34.7033363
        val lon = -58.3953235
        val nombre = "Lanus"
        binding.tvCiudad.text = nombre
        binding.progressBar.visibility = View.VISIBLE

        // Llamadas dentro de corrutina utilizando lifecycleScope
        lifecycleScope.launch {
            try {
                // Cargar clima actual
                val climaActual = climaViewModel.loadClimaActual(lat, lon, "metric")
                if (climaActual != null) {
                    updateUIWithCurrentWeather(climaActual)
                } else {
                    Toast.makeText(this@MainActivity, "Error al cargar clima actual", Toast.LENGTH_SHORT).show()
                }

                // Cargar pronóstico del clima
                val forecastData = climaViewModel.loadForecastClima(lat, lon, "metric")
                if (forecastData != null) {
                    updateUIWithForecast(forecastData)
                } else {
                    Toast.makeText(this@MainActivity, "Error al cargar pronóstico", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUIWithCurrentWeather(data: CurrentResponseApi) {
        binding.progressBar.visibility = View.GONE
        binding.LLContenedorDatos.visibility = View.VISIBLE
        // Manejo de datos de respuesta
        binding.tvClima.text = data.weather?.get(0)?.main ?: "-"
        binding.tvPorcentajeViento.text = data.wind?.speed?.let { Math.round(it).toString() } + "Km"
        binding.tvPorcentajeHumedad.text = data.main?.humidity?.toString() + "%"
        binding.tvGrados.text = data.main?.temp?.let { Math.round(it).toString() } + "°"
        binding.tvMaxGrados.text = "Max:" + data.main?.tempMax?.let { Math.round(it).toString() } + "°"
        binding.tvMinGrados.text = "Min:" + data.main?.tempMin?.let { Math.round(it).toString() } + "°"
    }

    private fun updateUIWithForecast(data: ForecastResponseApi) {
        binding.rvForecastView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = forecastAdapter
        }
        forecastAdapter.differ.submitList(data.list)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.item_add){
            var intent = Intent(this,ListaCiudadesActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

}
