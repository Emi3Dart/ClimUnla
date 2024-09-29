package com.example.climunla.data.entities
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "usuario",
    indices = [Index(value = ["nombre_usuario"], unique = true)]  // Añadir índice único
)
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    @ColumnInfo(name = "apellido") val apellido: String,

    @ColumnInfo(name = "nombre") val nombre: String,

    @ColumnInfo(name = "nombre_usuario") val nombreUsuario: String,

    @ColumnInfo(name = "contrasenia") val contrasenia: String
)
