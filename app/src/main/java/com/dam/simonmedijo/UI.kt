package com.dam.simonmedijo

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun IU(viewModel: MyVM) {
    Botonera(viewModel)
}

@Composable
fun Botonera(myVM: MyVM) {
    // Estados observables
    val currentColor: Colores? = Datos.currentColorEncendido.collectAsState().value
    val estado = Datos.estado.collectAsState().value
    val rondaActual = Datos.ronda.collectAsState().value
    val recordActual = Datos.record.collectAsState().value

    // Estado para el record con fecha
    var recordConFecha by remember { mutableStateOf<Record?>(null) }

    // Cargar el record con fecha cuando la UI se monte
    LaunchedEffect(Unit) {
        // TODO: Obtener el record del ViewModel
        // Por ahora, cargamos directamente (esto cambiará)
        // recordConFecha = myVM.obtenerRecordConFecha()
    }

    val botoneraIsActive = estado.botoneraIsActive
    val botonStartIsActive = estado.botonStartIsActive

    @Composable
    fun buttonColorsFor(base: Color, isLit: Boolean): ButtonColors {
        val finalColor = when (estado) {
            Estado.GENERAR_SECUENCIA -> if (isLit) base else base.copy(
                red = base.red * 0.25f,
                green = base.green * 0.25f,
                blue = base.blue * 0.25f
            )
            Estado.ELECCION_USUARIO -> base
            else -> base.copy(
                red = base.red * 0.25f,
                green = base.green * 0.25f,
                blue = base.blue * 0.25f
            )
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
            Estado.IDLE -> false
            Estado.GENERAR_SECUENCIA -> currentColor == color
            Estado.ELECCION_USUARIO -> true
            Estado.FINALIZADO -> false
        }
    }

    var colorParaSonar by remember { mutableStateOf<Colores?>(null) }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // FILA SUPERIOR: Ronda y Record
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Contador Izquierda - Ronda actual
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ronda actual",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = rondaActual.toString(),
                    fontSize = 24.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    modifier = Modifier
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(12.dp))
                        .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                )
            }

            // Contador Derecha - Record con fecha
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Mejor récord",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = recordActual.toString(),
                    fontSize = 24.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    modifier = Modifier
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(12.dp))
                        .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                )

                // Mostrar fecha del record si existe
                recordConFecha?.fechaTexto?.let { fecha ->
                    Text(
                        text = "Conseguido: $fecha",
                        fontSize = 10.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        // BOTONES DE COLORES
        Row(
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            // Rojo
            Button(
                enabled = botoneraIsActive,
                onClick = {
                    myVM.colorSeleccionado(Colores.CLASE_ROJO)
                    colorParaSonar = Colores.CLASE_ROJO
                },
                modifier = Modifier
                    .size(150.dp)
                    .padding(4.dp),
                colors = buttonColorsFor(
                    base = Colores.CLASE_ROJO.color,
                    isLit = debeIluminar(Colores.CLASE_ROJO)
                )
            ) {
                Text("Rojo", fontSize = 18.sp)
            }

            // Verde
            Button(
                enabled = botoneraIsActive,
                onClick = {
                    myVM.colorSeleccionado(Colores.CLASE_VERDE)
                    colorParaSonar = Colores.CLASE_VERDE
                },
                modifier = Modifier
                    .size(150.dp)
                    .padding(4.dp),
                colors = buttonColorsFor(
                    base = Colores.CLASE_VERDE.color,
                    isLit = debeIluminar(Colores.CLASE_VERDE)
                )
            ) {
                Text("Verde", fontSize = 18.sp)
            }
        }

        Row(
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            // Azul
            Button(
                enabled = botoneraIsActive,
                onClick = {
                    myVM.colorSeleccionado(Colores.CLASE_AZUL)
                    colorParaSonar = Colores.CLASE_AZUL
                },
                modifier = Modifier
                    .size(150.dp)
                    .padding(4.dp),
                colors = buttonColorsFor(
                    base = Colores.CLASE_AZUL.color,
                    isLit = debeIluminar(Colores.CLASE_AZUL)
                )
            ) {
                Text("Azul", fontSize = 18.sp)
            }

            // Morado
            Button(
                enabled = botoneraIsActive,
                onClick = {
                    myVM.colorSeleccionado(Colores.CLASE_MORADO)
                    colorParaSonar = Colores.CLASE_MORADO
                },
                modifier = Modifier
                    .size(150.dp)
                    .padding(4.dp),
                colors = buttonColorsFor(
                    base = Colores.CLASE_MORADO.color,
                    isLit = debeIluminar(Colores.CLASE_MORADO)
                )
            ) {
                Text("Morado", fontSize = 18.sp)
            }
        }

        // Sonidos
        colorParaSonar?.let { color ->
            ejecutarSonido(color)
            colorParaSonar = null
        }

        // BOTÓN START/REINICIAR
        Button(
            enabled = botonStartIsActive,
            onClick = {
                when (estado) {
                    Estado.IDLE -> myVM.iniciarJuego()
                    Estado.FINALIZADO -> myVM.volverAlIdle()
                    else -> { /* No hacer nada */ }
                }
            },
            modifier = Modifier
                .size(width = 200.dp, height = 60.dp)
                .padding(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text(
                text = when (estado) {
                    Estado.IDLE -> "INICIAR JUEGO"
                    Estado.FINALIZADO -> "REINICIAR"
                    else -> "GENERANDO..."
                },
                fontSize = 18.sp,
                color = Color.White
            )
        }

        // PANEL DE ESTADO
        Text(
            text = estado.textoPanel,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .border(2.dp, Color.Black, shape = RoundedCornerShape(12.dp))
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            textAlign = TextAlign.Center
        )

        LaunchedEffect(estado) {
            if (estado == Estado.FINALIZADO) {
                colorParaSonar = null
            }
        }
    }
}

@Composable
fun ejecutarSonido(color: Colores) {
    LaunchedEffect(color) {
        val sonidoError = when ((1..4).random()) {
            1 -> Datos.sonidoError1
            2 -> Datos.sonidoError2
            3 -> Datos.sonidoError3
            4 -> Datos.sonidoError4
            else -> Datos.sonidoError1
        }

        val sonidoAcierto = when (color) {
            Colores.CLASE_ROJO -> Datos.sonidoRojo
            Colores.CLASE_VERDE -> Datos.sonidoVerde
            Colores.CLASE_AZUL -> Datos.sonidoAzul
            Colores.CLASE_MORADO -> Datos.sonidoAmarillo
        }

        val sonido = when(Datos.estado.value) {
            Estado.GENERAR_SECUENCIA -> sonidoAcierto
            Estado.ELECCION_USUARIO -> sonidoAcierto
            Estado.FINALIZADO -> sonidoError
            else -> sonidoAcierto
        }

        Datos.soundPool.play(sonido, 1f, 1f, 0, 0, 1f)
        delay(1500)
    }
}

@Preview(showBackground = true)
@Composable
fun ViewAll() {
    val mockViewModel = MyVM()
    IU(mockViewModel)
}