package com.example.vapeogame

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PuntajeVapeoViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppVapeoDatabase.getDatabase(application).puntajeDao()
    val todosLosPuntajes: LiveData<List<PuntajeVapeoEntity>> = dao.obtenerTodos()

    fun guardarPuntaje(jugador: String, puntos: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val registro = PuntajeVapeoEntity(
                jugador = jugador,
                puntos = puntos
            )
            dao.insertarPuntaje(registro)
        }
    }
}