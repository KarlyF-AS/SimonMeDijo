package com.dam.simonmedijo

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IU() {
    Botonera()
}

/**
 * Botones de colores
 */
@Composable
fun Botonera() {
    Row {
        // Cuatro botones centrados en la pantalla
        for (color in Colores.values()) {
            Log.d("Juego", "Botonera: ${color.ordinal}")
            Boton(color)
        }
    }
}
@Composable
fun Boton(enum_color: Colores) {
    Button(
        // utilizamos el color del enum
        colors =  ButtonDefaults.buttonColors(enum_color.color),
        onClick = { Log.d("Juego","Click!") },
        modifier = Modifier
            .size((100).dp, (60).dp)
            .padding(15.dp)
    ) {
        // utilizamos el texto del enum
        Text(text = enum_color.txt, fontSize = 10.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun ViewAll() {
    IU()
}