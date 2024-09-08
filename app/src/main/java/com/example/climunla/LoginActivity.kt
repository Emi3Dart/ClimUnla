package com.example.climunla

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import android.content.Intent

class LoginActivity : AppCompatActivity() {


    lateinit var lottieAnimacion : LottieAnimationView
    lateinit var etUsuario : EditText
    lateinit var etPassword : EditText
    lateinit var btnRegistrarse : Button
    lateinit var btnIniciarSesion : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        etUsuario = findViewById(R.id.et_nombre_usuario)
        etPassword = findViewById(R.id.et_contrasenia)
        btnRegistrarse = findViewById(R.id.btn_contrasenia)
        btnIniciarSesion = findViewById(R.id.btn_login)
        lottieAnimacion = findViewById(R.id.la_wave)

        lottieAnimacion.setSpeed(0.3f)
        lottieAnimacion.playAnimation() /// para que la animacion de fondo no vaya tan rapido

        btnRegistrarse.setOnClickListener {
            // Iniciar la actividad de registro
            val intent = Intent(this, RegistrarActivity2::class.java)
            startActivity(intent)
        }
        btnIniciarSesion.setOnClickListener {
            var usuario = etUsuario.text.toString()
            if(usuario.isEmpty() || etPassword.text.toString().isEmpty()){
                var mensaje = "Completar Datos"
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Iniciar sesion - TODO", Toast.LENGTH_SHORT).show()
                /*
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("NOMBRE", usuario)
                startActivity(intent)
                finish()
                */
            }

        }
    }
}