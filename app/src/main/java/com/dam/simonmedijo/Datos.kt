package com.dam.simonmedijo

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow


object Datos {
        var ronda = MutableStateFlow(0)
        var record = MutableStateFlow(0)
        var estado = MutableStateFlow(Estado.IDLE)
        var secuencia = MutableStateFlow(mutableListOf<Colores>())
}

    /**
     * Enum con los estados del juego
     *
     */

    enum class Estado(val botoneraIsActive: Boolean, val botonStartIsActive: Boolean) {
        IDLE( botoneraIsActive = false, botonStartIsActive = true ),
        GENERAR_SECUENCIA( botoneraIsActive = false, botonStartIsActive = false),
        ELECCION_USUARIO( botoneraIsActive = true, botonStartIsActive = false),
        FINALIZADO( botoneraIsActive = false, botonStartIsActive = true)
    }

    /**
     * Colores utilizados
     */

    enum class Colores(val color: Color, val txt: String) {
        CLASE_ROJO(color = Color.Red, txt = "Rojo"),
        CLASE_VERDE(color = Color.Green, txt = "Verde"),
        CLASE_AZUL(color = Color.Blue, txt = "Azul"),
        CLASE_MORADO(color = Color.Magenta, txt = "Mora");
        fun pasarColorANumero(): Int {
            return when (this) {
                CLASE_ROJO -> 0
                CLASE_VERDE -> 1
                CLASE_AZUL -> 2
                CLASE_MORADO -> 3
                else -> -1
            }
        }
    }


