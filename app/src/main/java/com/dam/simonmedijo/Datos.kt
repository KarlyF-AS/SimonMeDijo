package com.dam.simonmedijo

import androidx.compose.ui.graphics.Color


class Datos {
    object Datos {
        var ronda = 0
        var secuencia = mutableListOf<Int>()
        var secuenciaUsuario = mutableListOf<Int>()
        var record = 0
        var estado = Estado.IDLE
    }

    /**
     * Enum con los estados del juego
     *
     */

    enum class Estado {
        IDLE,
        GENERAR_SECUENCIA,
        ELECCION_USUARIO,
        FINALIZADO
    }

    /**
     * Colores utilizados
     */

    enum class Colores(val color: Color, val txt: String) {
        CLASE_ROJO(color = Color.Red, txt = "roxo"),
        CLASE_VERDE(color = Color.Green, txt = "verde"),
        CLASE_AZUL(color = Color.Blue, txt = "azul"),
        CLASE_MORADO(color = Color.Yellow, txt = "melo")
    }

}