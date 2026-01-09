package com.dam.simonmedijo

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dam.simonmedijo.data.RecordRoom
import kotlinx.coroutines.launch

class MyVM : ViewModel() {

    var posicion = 0

    private lateinit var historialRecord: RecordRoom
    private val _recordConFecha = mutableStateOf<Record?>(null)
    val recordConFecha: State<Record?> get() = _recordConFecha

    /** Inicializa Room para manejar récords */
    fun inicializarHistorial(context: Context) {
        historialRecord = RecordRoom(context)
        cargarRecordInicial()
    }

    /** Carga el récord guardado al iniciar la app */
    private fun cargarRecordInicial() {
        viewModelScope.launch {
            val record = historialRecord.cargarRecord()
            if (record != null) {
                Datos.record.value = record.maxRonda
                _recordConFecha.value = record
            }
        }
    }

    /** Comprueba si la elección del usuario es correcta */
    fun comprobarEleccionEnSecuencia(color: Colores, numeroSecuencia: Int): Boolean {
        return Datos.secuencia.value[numeroSecuencia] == color
    }

    /** Muestra la secuencia de colores */
    fun realizarSecuencia() {
        viewModelScope.launch {
            Datos.estado.value = Estado.GENERAR_SECUENCIA

            for(color in Datos.secuencia.value) {
                Datos.currentColorEncendido.value = color
                kotlinx.coroutines.delay(1000)
                Datos.currentColorEncendido.value = null
                kotlinx.coroutines.delay(1000)
            }
            Datos.estado.value = Estado.ELECCION_USUARIO
        }
    }

    /** Añade un color aleatorio a la secuencia */
    fun añadirColorASecuencia() {
        val colorAleatorio = Colores.entries.toTypedArray().random()
        Datos.secuencia.value.add(colorAleatorio)
    }

    /** Inicia el juego */
    fun iniciarJuego() {
        añadirColorASecuencia()
        realizarSecuencia()
    }

    /** Lógica cuando el usuario selecciona un color */
    fun colorSeleccionado(colorSelect: Colores) {
        if (comprobarEleccionEnSecuencia(colorSelect, posicion)) {
            posicion++
            if (posicion == Datos.secuencia.value.size) {
                Datos.ronda.value++
                añadirColorASecuencia()
                realizarSecuencia()
                posicion = 0
            }
        } else {
            Datos.secuencia.value = mutableListOf()
            comprobarRecord()
            Datos.ronda.value = 0
            posicion = 0
            Datos.estado.value = Estado.FINALIZADO
        }
    }

    /** Vuelve al estado inicial IDLE */
    fun volverAlIdle() {
        Datos.estado.value = Estado.IDLE
    }

    /** Comprueba si la ronda actual supera el récord y lo guarda usando Room */
    fun comprobarRecord() {
        if (Datos.ronda.value > Datos.record.value) {
            Datos.record.value = Datos.ronda.value
            val nuevoRecord = Record.crearDesdeRonda(Datos.ronda.value)
            _recordConFecha.value = nuevoRecord

            // Guardar récord en Room de forma segura
            viewModelScope.launch {
                historialRecord.guardarRecord(nuevoRecord)
            }
        }
    }
}
