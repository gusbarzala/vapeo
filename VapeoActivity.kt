package com.example.salomongames

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlin.math.sqrt

class VapeoActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var viewModel: PuntajeViewModel
    private lateinit var sensorManager: SensorManager
    private var acelerometro: Sensor? = null

    private lateinit var tvPuntaje: TextView
    private lateinit var tvAlerta: TextView
    private lateinit var btnJugar: Button

    private var puntaje = 0
    private var jugando = false
    private var ultimoSacudon = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vapeo)

        val nombreJugador = intent.getStringExtra("NOMBRE_JUGADOR") ?: "Jugador"
        viewModel = ViewModelProvider(this)[PuntajeViewModel::class.java]

        tvPuntaje = findViewById(R.id.tvPuntajeVapeo)
        tvAlerta = findViewById(R.id.tvAlertaVapeo)
        btnJugar = findViewById(R.id.btnJugarVapeo)

        val btnVapeoAgua = findViewById<Button>(R.id.btnVapeoAgua)
        val btnMarihuana = findViewById<Button>(R.id.btnMarihuana)
        val btnCigarrillos = findViewById<Button>(R.id.btnCigarrillos)
        val btnAntifumador = findViewById<Button>(R.id.btnAntifumador)
        val btnVolver = findViewById<Button>(R.id.btnVolverVapeo)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        btnJugar.setOnClickListener {
            jugando = true
            puntaje = 0
            tvPuntaje.text = "■ SALUD: 0 / 100 PTS"
            tvAlerta.text = "■ JUEGO INICIADO: ELEGÍ HÁBITOS SALUDABLES"
            vibrar(200)
        }

        btnVapeoAgua.setOnClickListener { evaluarOpcion(false, "▲ VAPEO DE AGUA (-15 PTS)", -15, nombreJugador) }
        btnMarihuana.setOnClickListener { evaluarOpcion(false, "▲ MARIHUANA (-20 PTS)", -20, nombreJugador) }
        btnCigarrillos.setOnClickListener { evaluarOpcion(false, "▲ CIGARRILLOS (-25 PTS)", -25, nombreJugador) }
        btnAntifumador.setOnClickListener { evaluarOpcion(true, "★ ANTIFUMADOR (+35 PTS)", 35, nombreJugador) }

        btnVolver.setOnClickListener {
            vibrar(100)
            finish()
        }
    }

    private fun evaluarOpcion(esSaludable: Boolean, mensaje: String, puntos: Int, jugador: String) {
        if (!jugando) {
            tvAlerta.text = "■ TOCÁ EL BOTÓN [JUGAR]"
            vibrar(100)
            return
        }

        puntaje = (puntaje + puntos).coerceAtLeast(0)
        tvAlerta.text = mensaje

        if (esSaludable) vibrar(250) else vibrarPatron(longArrayOf(0, 150, 100, 150))

        if (puntaje >= 100) {
            puntaje = 100
            jugando = false
            tvPuntaje.text = "■ SALUD: 100 / 100 PTS"
            tvAlerta.text = "★ ¡VICTORIA! 100 PTS GUARDADOS."
            vibrarPatron(longArrayOf(0, 300, 150, 500))
            viewModel.guardarPuntaje(jugador, "Prevención Vapeo", 100)
        } else {
            tvPuntaje.text = "■ SALUD: $puntaje / 100 PTS"
        }
    }

    override fun onResume() {
        super.onResume()
        acelerometro?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (!jugando || event == null) return
        val x = event.values[0]; val y = event.values[1]; val z = event.values[2]
        val aceleracion = sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH
        val ahora = System.currentTimeMillis()

        if (aceleracion > 11f && (ahora - ultimoSacudon) > 1200) {
            ultimoSacudon = ahora
            evaluarOpcion(true, "★ AGITASTE PARA DISIPAR HUMO (+25 PTS)", 25, intent.getStringExtra("NOMBRE_JUGADOR") ?: "Jugador")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

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

    private fun vibrarPatron(patron: LongArray) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(patron, -1))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(patron, -1)
        }
    }
}