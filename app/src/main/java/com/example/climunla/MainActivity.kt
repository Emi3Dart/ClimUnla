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
import com.example.climunla.data.retrofit.model.CurrentResponseApi
import com.example.climunla.data.retrofit.viewModel.ClimaViewModel
import com.example.climunla.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {
    ///
    lateinit var toolbar:Toolbar
    lateinit var binding: ActivityMainBinding
    private val climaViewModel : ClimaViewModel by viewModels()
    //lateinit var btnLunes : Button
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

        binding.apply {
            var lat = -34.7033363
            var lon = -58.3953235
            var nombre = "Lanus"

            tvCiudad.text = nombre
            progressBar.visibility = View.VISIBLE
            climaViewModel.loadClimaActual(lat,lon,"metric").enqueue(object :
                retrofit2.Callback<CurrentResponseApi> {
                override fun onResponse(
                    call: Call<CurrentResponseApi>,
                    response: Response<CurrentResponseApi>
                ) {
                    if(response.isSuccessful){
                        val data = response.body()
                        progressBar.visibility = View.GONE
                        LLContenedorDatos.visibility = View.VISIBLE
                        data?.let {
                            tvClima.text = it.weather?.get(0)?.main?:"-"
                            tvViento.text = it.wind?.speed?.let { Math.round(it).toString() } +"Km"
                            tvGrados.text = it.main?.temp?.let { Math.round(it).toString() }+"°"
                            tvMaxGrados.text = it.main?.tempMax?.let { Math.round(it).toString() }+"°"
                            tvMinGrados.text = it.main?.tempMin?.let { Math.round(it).toString() }+"°"



                        }
                    }
                }

                override fun onFailure(call: Call<CurrentResponseApi>, t: Throwable) {
                    Toast.makeText(this@MainActivity,t.toString(),Toast.LENGTH_SHORT).show()
                }

            })
        }



        //btnLunes = findViewById(R.id.btnLunes)

        /*btnLunes.setOnClickListener {
            val intent = Intent(this, Detalle_dia::class.java)
            startActivity(intent)
        }*/
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.item_add){
            var intent = Intent(this,Detalle_dia::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}