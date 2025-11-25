package com.dam.simonmedijo

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyVM : ViewModel(){

    var posicion = 0

    fun clickBoton(color: Colores) {
        if(comprobarEleccionEnSecuencia(color, posicion)){
            //Aqui realizar distintos cambios
            posicion++
        }else{
            //Aqui se fallo
            posicion = 0
        }
    }

    fun comprobarEleccionEnSecuencia(color: Colores, numeroSecuencia: Int):Boolean{
        if(Datos.secuencia.value[numeroSecuencia] == color){
            return true
        }
        return false
    }

    fun realizarSecuencia(){
        viewModelScope.launch {
            Datos.estado.value = Estado.GENERAR_SECUENCIA
            Log.d("App", "Estado de la secuencia: ${Datos.estado.value}")

            for(color in Datos.secuencia.value){
                Datos.currentColorEncendido.value = color
                delay(1000)
                Datos.currentColorEncendido.value = null
                delay(1000)
            }
            Datos.estado.value = Estado.ELECCION_USUARIO
            Log.d("App", "Estado de la secuencia: ${Datos.estado.value}")
        }
    }

    fun desactivarBotonera(){
        TODO()
    }

    fun añadirColorASecuencia(){
        var colorAleatorio  = Colores.entries.toTypedArray().random()
        Datos.secuencia.value.add(colorAleatorio)
    }

    fun generarSecuencia(){
        Datos.secuencia.value = mutableListOf( Colores.CLASE_ROJO, Colores.CLASE_VERDE, Colores.CLASE_AZUL, Colores.CLASE_MORADO)
    }

    fun iniciarJuego(){
        generarSecuencia()
        realizarSecuencia()
    }

    fun colorSeleccionado(colorSelect:Colores){
        if(comprobarEleccionEnSecuencia(colorSelect, posicion)){
            posicion++
        }else{
            posicion = 0
            Log.d("App", "ERROR")
            Datos.estado.value = Estado.FINALIZADO
        }

    }





}








