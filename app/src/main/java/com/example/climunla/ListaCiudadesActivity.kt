package com.example.climunla

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climunla.data.retrofit.adapter.CityAdapter
import com.example.climunla.data.retrofit.model.CityResponseApi
import com.example.climunla.data.retrofit.viewModel.CityViewModel
import com.example.climunla.databinding.ActivityListaCiudadesBinding
import com.example.climunla.databinding.CityViewholderBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaCiudadesActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var binding: ActivityListaCiudadesBinding
    private val cityAdapter by lazy { CityAdapter() }
    private val cityViewModel : CityViewModel by viewModels()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityListaCiudadesBinding.inflate(layoutInflater)

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
            etBuscarCiudad.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    progressBarBuscarCiudad.visibility=View.VISIBLE
                    cityViewModel.loadCity(p0.toString(),10).enqueue(object :Callback<CityResponseApi>{
                        override fun onResponse(
                            call: Call<CityResponseApi>,
                            response: Response<CityResponseApi>
                        ) {
                            if(response.isSuccessful){
                                val data = response.body()
                                data?.let {
                                    progressBarBuscarCiudad.visibility = View.GONE
                                    cityAdapter.differ.submitList(it)
                                    rvListaCiudades.apply {
                                        layoutManager = LinearLayoutManager(this@ListaCiudadesActivity,LinearLayoutManager.HORIZONTAL,false)
                                        adapter = cityAdapter
                                    }
                                }
                            }
                        }

                        override fun onFailure(call: Call<CityResponseApi>, t: Throwable) {
                            progressBarBuscarCiudad.visibility = View.GONE

                        }

                    })
                }

            })
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_return, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.item_volver){
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}