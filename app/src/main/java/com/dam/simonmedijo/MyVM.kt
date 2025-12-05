package com.dam.simonmedijo

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.State

class MyVM : ViewModel() {

    var posicion = 0
    private lateinit var historialRecord: HistorialRecord
    private val _recordConFecha = mutableStateOf<Record?>(null)
    val recordConFecha: State<Record?> get() = _recordConFecha

    fun inicializarHistorial(context: Context) {
        historialRecord = RecordSharedP(context)
        cargarRecordInicial()
    }


    private fun cargarRecordInicial() {
        viewModelScope.launch {
            val record = historialRecord.cargarRecord()
            if (record != null) {
                Datos.record.value = record.maxRonda
                _recordConFecha.value = record
            }
        }
    }



    fun comprobarEleccionEnSecuencia(color: Colores, numeroSecuencia: Int): Boolean {
        return Datos.secuencia.value[numeroSecuencia] == color
    }


    fun realizarSecuencia() {
        viewModelScope.launch {
            Datos.estado.value = Estado.GENERAR_SECUENCIA
            Log.d("App", "Estado de la secuencia: ${Datos.estado.value}")

            for(color in Datos.secuencia.value) {
                Datos.currentColorEncendido.value = color
                delay(1000)
                Datos.currentColorEncendido.value = null
                delay(1000)
            }
            Datos.estado.value = Estado.ELECCION_USUARIO
            Log.d("App", "Estado de la secuencia: ${Datos.estado.value}")
        }
    }


    fun añadirColorASecuencia() {
        val colorAleatorio = Colores.entries.toTypedArray().random()
        Datos.secuencia.value.add(colorAleatorio)
    }


    fun iniciarJuego() {
        añadirColorASecuencia()
        realizarSecuencia()
    }

    fun colorSeleccionado(colorSelect: Colores) {
        if(comprobarEleccionEnSecuencia(colorSelect, posicion)) {
            posicion++
            if(posicion == Datos.secuencia.value.size) {
                Log.d("App", "Completaste una ronda")
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
            Log.d("App", "ERROR")
            Datos.estado.value = Estado.FINALIZADO
        }
    }


    fun volverAlIdle() {
        Datos.estado.value = Estado.IDLE
    }

    fun comprobarRecord() {
        if(Datos.ronda.value > Datos.record.value) {
            Datos.record.value = Datos.ronda.value
            val nuevoRecord = Record.crearDesdeRonda(Datos.ronda.value)
            _recordConFecha.value = nuevoRecord  // ¡Actualiza el estado!
            historialRecord.guardarRecord(nuevoRecord)
        }
    }
}