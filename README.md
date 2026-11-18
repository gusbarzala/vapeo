# Prevención Vapeo - Aplicación Móvil Educativa

Aplicación móvil desarrollada en **Kotlin** para Android con arquitectura nativa, diseñada como parte del proyecto de prevención de consumo. Su objetivo principal es concientizar sobre los riesgos asociados al vapeo y promover estilos de vida saludables mediante gamificación y accesibilidad háptica.

## Características Principales

* **Pantalla de Inicio con Botón JUGAR:** Permite iniciar la sesión de evaluación interactiva de manera rápida y directa.
* **4 Formas de Consumo / Prevención Evaluadas:**
  1. **Vapeo de agua** (Impacto negativo en salud y puntaje).
  2. **Marihuana** (Evaluación de factores de riesgo).
  3. **Fumador como cigarrillos** (Comparativa de daños pulmonares).
  4. **Antifumador** (Opción saludable que otorga bonificación positiva).
* **Sensor de Acelerómetro Integrado:** Detecta cuando el usuario agita el dispositivo para simular acciones interactivas dentro de la temática de prevención.
* **Feedback Háptico Avanzado:** Patrones de vibración personalizados mediante `Vibrator` y `VibrationEffect` que se activan según la opción seleccionada.
* **Persistencia Local de Datos (Room Database):** Almacena automáticamente los registros de puntaje y los datos del jugador utilizando base de datos local y `ViewModel` con corrutinas en segundo plano.

## Stack Tecnológico

* **Lenguaje:** Kotlin
* **Interfaz de Usuario:** XML layouts con soporte responsivo (`ScrollView`, `LinearLayout`, botones estilizados).
* **Arquitectura:** MVVM (Model-View-ViewModel) con AndroidX Lifecycle.
* **Persistencia:** Room Persistence Library (SQLite).
* **Hardware:** Android Sensor Framework (Sensor.TYPE_ACCELEROMETER).
* **Entorno de Trabajo:** GitHub Codespaces / Android Studio.

## Estructura del Proyecto

* `VapeoActivity.kt`: Lógica principal del juego, manejo de opciones, puntajes y eventos del sensor acelerómetro.
* `activity_vapeo.xml`: Interfaz gráfica interactiva con los botones de control.
* `PuntajeVapeoEntity.kt`: Entidad de la tabla de base de datos local.
* `PuntajeVapeoDao.kt`: Consultas e inserciones en base de datos.
* `AppVapeoDatabase.kt`: Instancia Singleton de Room.
* `PuntajeVapeoViewModel.kt`: Gestor de datos y comunicación asíncrona.