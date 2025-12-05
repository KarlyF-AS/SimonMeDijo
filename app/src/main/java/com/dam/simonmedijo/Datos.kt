package com.dam.simonmedijo

import android.content.Context
import android.media.SoundPool
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object Datos {

        var ronda = MutableStateFlow(0) // La ronda actual del juego
        var record = MutableStateFlow(0) // El record persistente del juego
        var estado = MutableStateFlow(Estado.IDLE) // El estado del juego
        var secuencia = MutableStateFlow(mutableListOf<Colores>()) //La lista de colores que hay en cada ronda

        var currentColorEncendido: MutableStateFlow<Colores?> = MutableStateFlow(null) // El color que se va a encender en cada fase de la ronda

        lateinit var soundPool: SoundPool
        var sonidoVerde = 0
        var sonidoRojo = 0
        var sonidoAzul = 0
        var sonidoAmarillo = 0
        var sonidoError1 = 0
        var sonidoError2 = 0
        var sonidoError3 = 0
        var sonidoError4 = 0

}


enum class Estado(val botoneraIsActive: Boolean, val botonStartIsActive: Boolean, val textoPanel: String) {
        IDLE( botoneraIsActive = false, botonStartIsActive = true, textoPanel = "Presiona Start para comenzar" ),
        GENERAR_SECUENCIA( botoneraIsActive = false, botonStartIsActive = false, textoPanel = "Generando secuencia"),
        ELECCION_USUARIO( botoneraIsActive = true, botonStartIsActive = false, textoPanel = "Adivina la secuencia"),
        FINALIZADO( botoneraIsActive = false, botonStartIsActive = true, textoPanel = "Fallaste, vuelve a intentarlo")
    }


    enum class Colores(val color: Color, val txt: String) {
        CLASE_ROJO(color = Color.Red, txt = "Rojo"),
        CLASE_VERDE(color = Color.Green, txt = "Verde"),
        CLASE_AZUL(color = Color.Blue, txt = "Azul"),
        CLASE_MORADO(color = Color.Magenta, txt = "Mora")
    }

/**
 * Record
 */
data class Record(
    val maxRonda: Int = 0,
    val fechaTexto: String = "",
    val tiempoMS: Long = 0
) {
    companion object {
        // Esto crea un Record con la fecha ACTUAL
        fun crearDesdeRonda(rondaActual: Int): Record {
            val ahora = Date()
            val formato = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            return Record(
                maxRonda = rondaActual,
                fechaTexto = formato.format(ahora),
                tiempoMS = ahora.time
            )
        }
    }
}

fun inicializarSonidos(context: Context) { // El contexto es necesario para cargar los sonidos
    Datos.soundPool = SoundPool.Builder()
        .setMaxStreams(8) // Aumentado para manejar más sonidos simultáneos
        .build()
    Datos.sonidoVerde = Datos.soundPool.load(context, R.raw.do_alto, 1)
    Datos.sonidoRojo = Datos.soundPool.load(context, R.raw.mi, 1)
    Datos.sonidoAzul = Datos.soundPool.load(context, R.raw.sol, 1)
    Datos.sonidoAmarillo = Datos.soundPool.load(context, R.raw.do_bajo, 1)
    Datos.sonidoError1 = Datos.soundPool.load(context, R.raw.error_1, 1)
    Datos.sonidoError2 = Datos.soundPool.load(context, R.raw.error_2, 1)
    Datos.sonidoError3 = Datos.soundPool.load(context, R.raw.error_3, 1)
    Datos.sonidoError4 = Datos.soundPool.load(context, R.raw.error_4, 1)
}

