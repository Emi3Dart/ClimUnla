package com.example.climunla.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.climunla.data.dao.UsuarioDao
import com.example.climunla.data.entities.Usuario

@Database(entities = [Usuario::class], version = 2)
abstract class UsuarioDataBase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    companion object {


        @Volatile
        private var INSTANCE: UsuarioDataBase? = null

        fun getInstance(context: Context): UsuarioDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsuarioDataBase::class.java,
                    "usuario_database"
                )
                    .fallbackToDestructiveMigration()  // Esto permite que la base de datos se destruya y recree
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
