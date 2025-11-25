package com.dam.simonmedijo

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
            for(color in Datos.secuencia.value){
                Datos.currentColorEncendido.value = color
                delay(1000)
                Datos.currentColorEncendido.value = null
                delay(1000)
            }
        }
    }

    fun desactivarBotonera(){
        TODO()
    }

    fun añadirColorASecuencia(){
        var colorAleatorio  = Colores.entries.toTypedArray().random()
        Datos.secuencia.value.add(colorAleatorio)
    }





}








