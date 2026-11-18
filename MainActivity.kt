package com.example.salomongames

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etJugador = findViewById<EditText>(R.id.etNombreJugador)
        val btnSalomon = findViewById<Button>(R.id.btnIrSalomon)
        val btnVapeo = findViewById<Button>(R.id.btnIrVapeo)

        btnSalomon.setOnClickListener {
            vibrar(150)
            val nombre = etJugador.text.toString().trim().ifEmpty { "Jugador" }
            val intent = Intent(this, AlquimiaActivity::class.java)
            intent.putExtra("NOMBRE_JUGADOR", nombre)
            startActivity(intent)
        }

        btnVapeo.setOnClickListener {
            vibrar(150)
            val nombre = etJugador.text.toString().trim().ifEmpty { "Jugador" }
            val intent = Intent(this, VapeoActivity::class.java)
            intent.putExtra("NOMBRE_JUGADOR", nombre)
            startActivity(intent)
        }
    }

    private fun vibrar(ms: Long) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(ms, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(ms)
        }
    }
}