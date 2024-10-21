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

class MainActivity : AppCompatActivity() {
    ///
    lateinit var toolbar:Toolbar// para la barra de herramientas
    lateinit var binding: ActivityMainBinding// para usar viewBinding para acceder y manipular los elementos de la interfaz
    private val climaViewModel : ClimaViewModel by viewModels()//un viewModel que se utiliza para cargar los datos del clima actual y el pronostico
    private val forecastAdapter by lazy { ForecastAdapter() }//adaptador para el pronostico del clima que maneja la vista en forma de lista horizontal.
    //lateinit var btnLunes : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)// Se infla el diseño de la actividad principal usando ActivityMainBinding
        enableEdgeToEdge()
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainFragment())
                .commit()
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Se establece un Toolbar personalizado con un título
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.titulo)

        binding.apply {
            //Recoge las coordenadas de latitud y longitud pasadas a través del Intent (del activity ListaCiudadesActivity),
            // o establece valores predeterminados para la ciudad de "Lanús" si no se proporcionan
            var lat = intent.getDoubleExtra("lat",0.0)
            var lon = intent.getDoubleExtra("lon",0.0)
            var nombre = intent.getStringExtra("name")

            if (lat == 0.0){
                 lat = -34.7033363
                 lon = -58.3953235
                 nombre = "Lanus"
            }


            /// clima hoy
            tvCiudad.text = nombre
            progressBar.visibility = View.VISIBLE
            //Hace una llamada a la API usando el climaViewModel para obtener el clima actual de la ciudad con las coordenadas especificadas.
            climaViewModel.loadClimaActual(lat,lon,"metric").enqueue(object :
                retrofit2.Callback<CurrentResponseApi> {
                override fun onResponse(
                    call: Call<CurrentResponseApi>,
                    response: Response<CurrentResponseApi>
                ) {
                    //Si la respuesta es exitosa, actualiza la interfaz con la información recibida (temperatura, humedad, velocidad del viento, etc.).
                    if(response.isSuccessful){
                        val data = response.body()
                        progressBar.visibility = View.GONE
                        LLContenedorDatos.visibility = View.VISIBLE
                        data?.let {

                            tvClima.text = it.weather?.get(0)?.main?:"-"
                            tvPorcentajeViento.text = it.wind?.speed?.let { Math.round(it).toString() } +"Km"
                            tvPorcentajeHumedad.text = it.main?.humidity?.toString()+"%"
                            tvGrados.text = it.main?.temp?.let { Math.round(it).toString() }+"°"
                            tvMaxGrados.text = "Max:"+ it.main?.tempMax?.let { Math.round(it).toString() }+"°"
                            tvMinGrados.text = "Min:"+it.main?.tempMin?.let { Math.round(it).toString() }+"°"


                            //Cambia la imagen del clima (ivClimaDia) según el tipo de clima (sol, nubes, lluvia, tormenta, etc.).
                            val weatherCondition = it.weather?.get(0)?.main
                            when (weatherCondition) {
                                "Clear" -> ivClimaDia.setImageResource(R.drawable.sunny)             // Despejado
                                "Clouds" -> ivClimaDia.setImageResource(R.drawable.cloudy)            // Nublado
                                "Rain", "Drizzle" -> ivClimaDia.setImageResource(R.drawable.rainy)    // Lluvia y llovizna
                                "Thunderstorm" -> ivClimaDia.setImageResource(R.drawable.storm)       // Tormenta
                                "Snow" -> ivClimaDia.setImageResource(R.drawable.snowy)               // Nieve
                                "Mist", "Smoke", "Haze", "Fog", "Dust", "Sand", "Ash", "Squall", "Tornado" ->
                                    ivClimaDia.setImageResource(R.drawable.windy)                     // Viento, neblina, humo, polvo, etc.
                                else -> ivClimaDia.setImageResource(R.drawable.cloudy_sunny)          // Imagen por defecto
                            }
                        }
                    }
                }
                //Si las llamadas a la API fallan, se muestra un Toast con el error correspondiente
                override fun onFailure(call: Call<CurrentResponseApi>, t: Throwable) {
                    Toast.makeText(this@MainActivity,t.toString(),Toast.LENGTH_SHORT).show()
                }

            })
            /// forecast clima
            //LLama  a la API para obtener el pronóstico del clima (forecast) y actualizar la interfaz de usuario en función de la respuesta recibida
            climaViewModel.loadForecastClima(lat,lon,"metric").enqueue(object :retrofit2.Callback<ForecastResponseApi>{
                override fun onResponse(
                    call: Call<ForecastResponseApi>,
                    response: Response<ForecastResponseApi>
                ) {
                    // la respuesta es exitosa, los datos del pronóstico se envían a un adaptador (forecastAdapter), que los muestra en una lista horizontal (RecyclerView).
                    if(response.isSuccessful){
                        val data = response.body()

                        data?.let {
                            forecastAdapter.differ.submitList(it.list)
                            rvForecastView.apply {
                                layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
                                adapter = forecastAdapter

                            }

                        }
                    }
                }

                override fun onFailure(call: Call<ForecastResponseApi>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })



        }



        //btnLunes = findViewById(R.id.btnLunes)

        /*btnLunes.setOnClickListener {
            val intent = Intent(this, Detalle_dia::class.java)
            startActivity(intent)
        }*/
    }

    //Se infla un menú de opciones que contiene una opción para agregar ciudades (abre la actividad ListaCiudadesActivity
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