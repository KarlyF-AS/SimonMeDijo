package com.dam.simonmedijo

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyVM : ViewModel(){
    fun comprobarEleccionEnSecuencia(color: Colores, numeroSecuencia: Int):Boolean{
        if(Datos.secuencia.value[numeroSecuencia] == color){
            return true
        }
        return false
    }


    fun realizarSecuencia(){
        viewModelScope.launch {
            for (color in Datos.secuencia.value){
                when(color){
                    Colores.CLASE_ROJO -> Colores.CLASE_ROJO.encendido.value = true
                    Colores.CLASE_VERDE -> Colores.CLASE_VERDE.encendido.value = true
                    Colores.CLASE_AZUL -> Colores.CLASE_AZUL.encendido.value = true
                    Colores.CLASE_MORADO -> Colores.CLASE_MORADO.encendido.value = true
                }
                delay(1000)
                desactivarBotonera()
            }
        }
    }

    fun desactivarBotonera(){
        for (color in Colores.entries){
            color.encendido.value = false
        }
    }

    fun añadirColorASecuencia(){
        var colorAleatorio  = Colores.entries.toTypedArray().random()
        Datos.secuencia.value.add(colorAleatorio)
    }



}








