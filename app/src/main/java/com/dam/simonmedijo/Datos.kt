package com.dam.simonmedijo

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow


object Datos {

        var ronda = MutableStateFlow(0) // La ronda actual del juego
        var record = MutableStateFlow(0) // El record persistente del juego
        var estado = MutableStateFlow(Estado.IDLE) // El estado del juego
        var secuencia = MutableStateFlow(mutableListOf<Colores>()) //La lista de colores que hay en cada ronda

        var currentColorEncendido: MutableStateFlow<Colores?> = MutableStateFlow(null) // El color que se va a encender en cada fase de la ronda


}

/**
 *  Estados del juego, para el manejo de botones y texto del panel
 *  @author Daniel Figueroa Vidal
 *  @param botoneraIsActive Boolean
 *  @param botonStartIsActive Boolean
 *  @param textoPanel String
 */

enum class Estado(val botoneraIsActive: Boolean, val botonStartIsActive: Boolean, val textoPanel: String) {
        IDLE( botoneraIsActive = false, botonStartIsActive = true, textoPanel = "Presiona Start para comenzar" ),
        GENERAR_SECUENCIA( botoneraIsActive = false, botonStartIsActive = false, textoPanel = "Generando secuencia"),
        ELECCION_USUARIO( botoneraIsActive = true, botonStartIsActive = false, textoPanel = "Adivina la secuencia"),
        FINALIZADO( botoneraIsActive = false, botonStartIsActive = true, textoPanel = "Fallaste, vuelve a intentarlo")
    }

    /**
     * Colores utilizados
     * @author Daniel Figueroa Vidal
     * @param color Color
     * @param txt String
     */
    enum class Colores(val color: Color, val txt: String) {
        CLASE_ROJO(color = Color.Red, txt = "Rojo"),
        CLASE_VERDE(color = Color.Green, txt = "Verde"),
        CLASE_AZUL(color = Color.Blue, txt = "Azul"),
        CLASE_MORADO(color = Color.Magenta, txt = "Mora")
    }




