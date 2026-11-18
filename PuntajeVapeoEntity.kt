package com.example.vapeogame

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "puntajes_vapeo")
data class PuntajeVapeoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val jugador: String,
    val juego: String = "Prevención Vapeo",
    val puntos: Int,
    val fecha: Long = System.currentTimeMillis()
)