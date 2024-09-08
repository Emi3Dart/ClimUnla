package com.example.climunla

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegistrarActivity2 : AppCompatActivity() {

    lateinit var nombre: EditText
    lateinit var apellido: EditText
    lateinit var etUsernombreR: EditText
    lateinit var etPasswordR: EditText
    lateinit var btnRegistrar: Button
    lateinit var btnLoguing: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar2)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar vistas
        nombre = findViewById(R.id.etNombre)
        apellido = findViewById(R.id.etApellido)
        etUsernombreR = findViewById(R.id.etNombreUsuario)
        etPasswordR = findViewById(R.id.etContrasenia)

        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnLoguing = findViewById(R.id.btnLogRegistrar)

        // Agregar listener al botón de registro
        btnRegistrar.setOnClickListener {
            val nombreText = nombre.text.toString()
            val apellidoText = apellido.text.toString()
            val usuarioText = etUsernombreR.text.toString()
            val passwordText = etPasswordR.text.toString()

            // Verificar si algún campo está vacío
            if (nombreText.isEmpty() || apellidoText.isEmpty() || usuarioText.isEmpty() || passwordText.isEmpty()) {
                // Mostrar un Toast si algún campo está vacío
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Crear un Intent para iniciar MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                // Opcional: Terminar la actividad actual para que el usuario no pueda volver a ella con el botón de retroceso
                finish()
            }
        }

        // Agregar listener al botón de login
        btnLoguing.setOnClickListener {
            // Crear un Intent para iniciar LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
    }
}
