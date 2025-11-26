package com.dam.simonmedijo

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.min

@Composable
fun IU() {
    val myVM = MyVM()
    Botonera(myVM)
}

/**
 * Botones de colores
 */
@Composable
fun Botonera(myVM: MyVM) {

    val currentColor = Datos.currentColorEncendido.collectAsState().value
    val estado = Datos.estado.collectAsState().value

    val botoneraIsActive = estado.botoneraIsActive
    val botonStartIsActive = estado.botonStartIsActive

    @Composable
    fun buttonColorsFor(base: Color, isLit: Boolean): ButtonColors {
        val finalColor = when (estado) {
            Estado.GENERAR_SECUENCIA -> if (isLit) base else base.copy(
                red = base.red * 0.5f + 0.5f,
                green = base.green * 0.5f + 0.5f,
                blue = base.blue * 0.5f + 0.5f
            ) // apagado claro, currentColorEncendido normal
            Estado.IDLE -> base.copy(
                red = base.red * 0.5f + 0.5f,
                green = base.green * 0.5f + 0.5f,
                blue = base.blue * 0.5f + 0.5f
            ) // apagado claro
            Estado.ELECCION_USUARIO -> base                     // todos normales
            Estado.FINALIZADO -> base.copy(
                red = base.red * 0.5f + 0.5f,
                green = base.green * 0.5f + 0.5f,
                blue = base.blue * 0.5f + 0.5f
            ) // apagado claro
        }

        return ButtonDefaults.buttonColors(
            containerColor = finalColor,
            contentColor = contentColorFor(finalColor),
            disabledContainerColor = finalColor,
            disabledContentColor = contentColorFor(finalColor)
        )
    }




    fun debeIluminar(color: Colores): Boolean {
        return when (estado) {
            Estado.IDLE -> false                                   // todos apagados
            Estado.GENERAR_SECUENCIA -> currentColor == color     // solo el currentColorEncendido
            Estado.ELECCION_USUARIO -> true                       // todos iluminados y clicables
            Estado.FINALIZADO -> false                            // todos apagados
        }
    }



    Column(modifier = Modifier.padding(16.dp)) {


        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Contador Izquierda
            Text(
                text = "Ronda: "+Datos.ronda.value.toString(), // Placeholder
                fontSize = 18.sp,
                modifier = Modifier
                    .border(2.dp, Color.Black, shape = RoundedCornerShape(12.dp))
                    .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Contador Derecha
            Text(
                text = "Récord: "+Datos.record.value.toString(), // Placeholder
                fontSize = 18.sp,
                modifier = Modifier
                    .border(2.dp, Color.Black, shape = RoundedCornerShape(12.dp))
                    .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        Row {
            // Rojo
            Button(
                enabled = botoneraIsActive,
                onClick = { myVM.colorSeleccionado(Colores.CLASE_ROJO) },
                modifier = Modifier.size(150.dp).padding(4.dp),
                colors = buttonColorsFor(
                    base = Colores.CLASE_ROJO.color,
                    isLit = debeIluminar(Colores.CLASE_ROJO)
                )
            ) { Text("Rojo", fontSize = 18.sp) }

            // Verde
            Button(
                enabled = botoneraIsActive,
                onClick = { myVM.colorSeleccionado(Colores.CLASE_VERDE) },
                modifier = Modifier.size(150.dp).padding(4.dp),
                colors = buttonColorsFor(
                    base = Colores.CLASE_VERDE.color,
                    isLit = debeIluminar(Colores.CLASE_VERDE)
                )
            ) { Text("Verde", fontSize = 18.sp) }
        }

        Row {
            // Azul
            Button(
                enabled = botoneraIsActive,
                onClick = { myVM.colorSeleccionado(Colores.CLASE_AZUL) },
                modifier = Modifier.size(150.dp).padding(4.dp),
                colors = buttonColorsFor(
                    base = Colores.CLASE_AZUL.color,
                    isLit = debeIluminar(Colores.CLASE_AZUL)
                )
            ) { Text("Azul", fontSize = 18.sp) }

            // Morado
            Button(
                enabled = botoneraIsActive,
                onClick = { myVM.colorSeleccionado(Colores.CLASE_MORADO) },
                modifier = Modifier.size(150.dp).padding(4.dp),
                colors = buttonColorsFor(
                    base = Colores.CLASE_MORADO.color,
                    isLit = debeIluminar(Colores.CLASE_MORADO)
                )
            ) { Text("Morado", fontSize = 18.sp) }
        }

        Row {
            Column {
                Button(
                    enabled = botonStartIsActive,
                    onClick = { if(Datos.estado.value == Estado.IDLE) myVM.iniciarJuego()
                              else if (Datos.estado.value == Estado.FINALIZADO) myVM.volverAlIdle()},
                    modifier = Modifier.size(150.dp).padding(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) { Text(text = if (Datos.estado.collectAsState().value == Estado.IDLE) "Inicio"
                    else if (Datos.estado.collectAsState().value == Estado.FINALIZADO)"Reiniciar" else "Generando", fontSize = 18.sp, color = Color.White) }
            }
        }
        Row{
            Text(
                text = Datos.estado.collectAsState().value.textoPanel,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color.Black, shape = RoundedCornerShape(12.dp))
                    .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ViewAll() {
    IU()
}