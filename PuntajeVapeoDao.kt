package com.example.vapeogame

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PuntajeVapeoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPuntaje(puntaje: PuntajeVapeoEntity)

    @Query("SELECT * FROM puntajes_vapeo ORDER BY fecha DESC")
    fun obtenerTodos(): LiveData<List<PuntajeVapeoEntity>>

    @Query("DELETE FROM puntajes_vapeo")
    suspend fun borrarTodo()
}