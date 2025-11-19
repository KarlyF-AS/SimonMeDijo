package com.dam.simonmedijo

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MyVM : ViewModel(){
    fun comprobarEleccionEnSecuencia(color: Colores, numeroSecuencia: Int):Boolean{
        if(Datos.secuencia.value[numeroSecuencia] == color){
            return true
        }
        return false
    }


    fun realizarSeuencia(){
        viewModelScope.launch {
            for (color in Datos.secuencia.value){


            }

        }



    }

    fun añadirColorASecuencia(){
        var colorAleatorio  = Colores.entries.toTypedArray().random()
        Datos.secuencia.value.add(colorAleatorio)
    }



}








