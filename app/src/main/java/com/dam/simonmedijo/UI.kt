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
    //Color actual que se va a encender
    val currentColor: Colores? = Datos.currentColorEncendido.collectAsState().value
    // Estado del juego
    val estado = Datos.estado.collectAsState().value

    //Manejo de "enabled" de botones
    val botoneraIsActive = estado.botoneraIsActive
    val botonStartIsActive = estado.botonStartIsActive

    /**
     * Determina los colores de un botón (fondo, contenido, deshabilitado) basándose en el estado actual del juego.
     * Permite que los botones se vean "iluminados" (color base) o "apagados" (color más claro/desaturado).
     * @param base El color base del botón (cuando está completamente iluminado).
     * @param isLit Indica si el botón debe mostrarse como iluminado.
     * @return Un objeto `ButtonColors` para ser usado en un `Button`.
     */
    @Composable
    fun buttonColorsFor(base: Color, isLit: Boolean): ButtonColors {
        // Determina el color final del botón según el estado del juego.
        val finalColor = when (estado) {
            Estado.GENERAR_SECUENCIA -> if (isLit) base else base.copy(
                red = base.red * 0.5f + 0.5f,
                green = base.green * 0.5f + 0.5f,
                blue = base.blue * 0.5f + 0.5f
            ) // En la secuencia, ilumina el botón actual y apaga los demás.
            Estado.ELECCION_USUARIO -> base                     // Durante la elección del usuario, todos los botones están iluminados y activos.
            else -> base.copy( // En IDLE y FINALIZADO, todos los botones están apagados (color claro).
                red = base.red * 0.5f + 0.5f,
                green = base.green * 0.5f + 0.5f,
                blue = base.blue * 0.5f + 0.5f
            )
        }
        // Retorna la configuración de colores. Se usa el mismo color para el estado deshabilitado para mantener la apariencia visual.
        return ButtonDefaults.buttonColors(
            containerColor = finalColor,
            contentColor = contentColorFor(finalColor),
            disabledContainerColor = finalColor,
            disabledContentColor = contentColorFor(finalColor)
        )
    }


    /**
     * Determina si un botón debe mostrarse como iluminado o no.
     * @param color El color del botón.
     */
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
            // Contador Izquierda, muestra la ronda actual
            Text(
                text = "Ronda: "+Datos.ronda.value.toString(),
                fontSize = 18.sp,
                modifier = Modifier
                    .border(2.dp, Color.Black, shape = RoundedCornerShape(12.dp))
                    .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Contador Derecha, muestra el record
            Text(
                text = "Récord: "+Datos.record.value.toString(),
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
        // Botón de start y de reinicio
        Row {
            Column {
                Button(
                    enabled = botonStartIsActive,
                    // Si estamos en IDLE iniciamos el juego y si estamos en FINALIZADO lo reiniciamos
                    onClick = { if(Datos.estado.value == Estado.IDLE) myVM.iniciarJuego()
                              else if (Datos.estado.value == Estado.FINALIZADO) myVM.volverAlIdle()},
                    modifier = Modifier.size(150.dp).padding(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    // si estamos en el IDLE cambiamos el texto a "Inicio" y si estamos en FINALIZADO cambiamos el texto a "Reiniciar"
                ) { Text(text = if (Datos.estado.collectAsState().value == Estado.IDLE) "Inicio"
                    else if (Datos.estado.collectAsState().value == Estado.FINALIZADO)"Reiniciar" else "Generando", fontSize = 18.sp, color = Color.White) }
            }
        }
        //Panel final
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