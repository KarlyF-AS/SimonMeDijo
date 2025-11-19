package com.dam.simonmedijo

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class MyVM : ViewModel(){
    fun comprobarEleccionEnSecuencia(color: Colores, numeroSecuencia: Int):Boolean{
        if(Datos.secuencia.value[numeroSecuencia] == color){
            return true
        }
        return false
    }


//    fun realizarSeuencia(){
//        MyVM.launch{
//
//        }
//
//
//
//    }

    fun añadirColorASecuencia(){
        var colorAleatorio  = Colores.entries.toTypedArray().random()
        Datos.secuencia.value.add(colorAleatorio)
    }



}









}