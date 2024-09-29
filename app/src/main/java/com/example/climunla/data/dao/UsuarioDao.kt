package com.example.climunla.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.climunla.data.entities.Usuario

@Dao
interface UsuarioDao {

    // Función para insertar un usuario
    @Insert(onConflict = OnConflictStrategy.ABORT)  // Aborta la transacción si hay conflicto
    suspend fun insertarUsuario(usuario: Usuario)

    // Función para verificar si ya existe un usuario con ese nombre de usuario
    @Query("SELECT * FROM usuario WHERE nombre_usuario = :nombreUsuario LIMIT 1")
    suspend fun buscarPorNombreUsuario(nombreUsuario: String): Usuario?
}
