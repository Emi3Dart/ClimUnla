package com.example.climunla

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
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
import android.os.Build
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.climunla.data.UsuarioDataBase
import com.example.climunla.data.dao.UsuarioDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class LoginActivity : AppCompatActivity() {

    lateinit var lottieAnimacion: LottieAnimationView
    lateinit var etUsuario: EditText
    lateinit var etPassword: EditText
    lateinit var btnIniciarSesion: Button
    lateinit var tvRegistrar: TextView
    private lateinit var db: UsuarioDataBase // Cambiado a lateinit para inicializar en onCreate
    private lateinit var usuarioDao: UsuarioDao // Cambiado a lateinit
    lateinit var cbRecordar: CheckBox


         private val CHANNEL_ID="myChanel"
         private val NOTIFICATION_ID = 1
         private val REQUEST_NOTIFICATION_PERMISSION = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Inicializar la base de datos y el DAO aquí
        db = UsuarioDataBase.getInstance(application) // Correcto, ahora el contexto está disponible
        usuarioDao = db.usuarioDao()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etUsuario = findViewById(R.id.et_nombre_usuario)
        etPassword = findViewById(R.id.et_contrasenia)
        btnIniciarSesion = findViewById(R.id.btn_login)
        lottieAnimacion = findViewById(R.id.la_wave)
        tvRegistrar = findViewById(R.id.tv_registrar)
        cbRecordar = findViewById(R.id.cb_recordar) // Nuevo: CheckBox

        lottieAnimacion.setSpeed(0.3f)
        lottieAnimacion.playAnimation()

        // Recuperar SharedPreferences
        val sharedPreferences = getSharedPreferences("acceso", MODE_PRIVATE)
        val recordarUsuario = sharedPreferences.getBoolean("recordar_usuario", false)

        if (recordarUsuario) {
            etUsuario.setText(sharedPreferences.getString("username", ""))
            etPassword.setText(sharedPreferences.getString("password", ""))
            cbRecordar.isChecked = true
        }

        tvRegistrar.setOnClickListener {
            // Iniciar la actividad de registro
            val intent = Intent(this, RegistrarActivity2::class.java)
            startActivity(intent)
            finish()
        }




        btnIniciarSesion.setOnClickListener {

            val usuario = etUsuario.text.toString()
            val contrasenia = etPassword.text.toString()

            if (usuario.isEmpty() || contrasenia.isEmpty()) {
                Toast.makeText(this, "Completar Datos", Toast.LENGTH_SHORT).show()
            } else {
                val editor = sharedPreferences.edit()
                if (cbRecordar.isChecked) {
                    editor.putString("username",usuario)
                    editor.putString("password", contrasenia)
                    editor.putBoolean("recordar_usuario", true)


                } else {
                    // Si no está marcado, limpiar los datos guardados
                    editor.remove("username")
                    editor.remove("password")
                    editor.putBoolean("recordar_usuario", false)
                }
                editor.apply()
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        // Verifica las credenciales en la base de datos
                        val usuarioEncontrado = usuarioDao.verificarCredenciales(usuario, contrasenia)

                        // Si el usuario es encontrado, se procede
                        if (usuarioEncontrado != null) {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra("NOMBRE", usuarioEncontrado.nombre)
                            startActivity(intent)
                            finish()
                        } else {
                            runOnUiThread {
                                Toast.makeText(this@LoginActivity, "Usuario y/o contraseña son incorrectos", Toast.LENGTH_SHORT).show()
                            }
                        }

                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(this@LoginActivity, "Error al iniciar sesión: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }


createChanel()

        cbRecordar.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkNotificationPermission()
            }
        }


    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                // Solicitar el permiso
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_NOTIFICATION_PERMISSION
                )
            } else {
                // Si ya se concedió, mostrar la notificación
                createNotificacion()
            }
        } else {
            // Si la versión es menor a Android 13, no es necesario solicitar permiso
            createNotificacion()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, mostrar la notificación
                createNotificacion()
            } else {
                // El permiso fue denegado, manejar el caso aquí si es necesario
            }
        }
    }





    private fun createNotificacion() {

        val builder =NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.checkbox_on_background)
            .setContentTitle("MARCASTE LA CAJA")
            .setContentText("USTED A SELECIONADO RECORDAR SU USAURIO Y CONTRASEÑA")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){

            notify(NOTIFICATION_ID, builder.build())



        }


    }
    fun createChanel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel=NotificationChannel(
               CHANNEL_ID,
                "MySuperChanel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager:NotificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
        } }







    }







