package com.example.climunla

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.climunla.data.UsuarioDataBase
import com.example.climunla.data.entities.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        val db = UsuarioDataBase.getInstance(applicationContext)
        val usuarioDao = db.usuarioDao()

        btnRegistrar.setOnClickListener {
            val nombreText = nombre.text.toString()
            val apellidoText = apellido.text.toString()
            val usuarioText = etUsernombreR.text.toString()
            val passwordText = etPasswordR.text.toString()

            // Verificar si algún campo está vacío
            if (nombreText.isEmpty() || apellidoText.isEmpty() || usuarioText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val nuevoUsuario = Usuario(
                    apellido = apellidoText,
                    nombre = nombreText,
                    nombreUsuario = usuarioText,
                    contrasenia = passwordText
                )

                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        usuarioDao.insertarUsuario(nuevoUsuario)  // Intentar insertar el usuario
                        Toast.makeText(this@RegistrarActivity2, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()

                        // Iniciar MainActivity después de la inserción
                        val intent = Intent(this@RegistrarActivity2, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } catch (e: SQLiteConstraintException) {
                        // Capturar la excepción si el usuario ya existe
                        Toast.makeText(this@RegistrarActivity2, "El nombre de usuario ya existe. Por favor, elija otro.", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        // Capturar cualquier otra excepción
                        Toast.makeText(this@RegistrarActivity2, "Ocurrió un error al registrar el usuario: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        // Agregar listener al botón de login
        btnLoguing.setOnClickListener {
            // Crear un Intent para iniciar LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Termina la actividad actual si es necesario
        }
    }
}

