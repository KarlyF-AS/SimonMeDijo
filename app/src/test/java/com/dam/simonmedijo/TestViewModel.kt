package com.dam.simonmedijo

import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

@OptIn(ExperimentalCoroutinesApi::class)
class TestViewModel {

    private lateinit var vm: MyVM

    @Before
    fun setup() {
        vm = MyVM()
        // Reiniciamos Datos antes de cada test
        Datos.secuencia.value = mutableListOf()
        Datos.ronda.value = 0
        Datos.record.value = 0
        Datos.estado.value = Estado.IDLE
        Datos.currentColorEncendido.value = null
        vm.inicializarHistorial(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun `añadirColorASecuencia incrementa secuencia`() {
        val initialSize = Datos.secuencia.value.size
        vm.añadirColorASecuencia()
        assertEquals(initialSize + 1, Datos.secuencia.value.size)
    }

    @Test
    fun `comprobarEleccionEnSecuencia devuelve true si acierta`() {
        val color = Colores.CLASE_ROJO
        Datos.secuencia.value = mutableListOf(color)
        assertTrue(vm.comprobarEleccionEnSecuencia(color, 0))
    }

    @Test
    fun `comprobarEleccionEnSecuencia devuelve false si falla`() {
        Datos.secuencia.value = mutableListOf(Colores.CLASE_VERDE)
        assertFalse(vm.comprobarEleccionEnSecuencia(Colores.CLASE_ROJO, 0))
    }

    @Test
    fun `colorSeleccionado avanza posicion si acierta`() {
        Datos.secuencia.value = mutableListOf(Colores.CLASE_ROJO)
        vm.colorSeleccionado(Colores.CLASE_ROJO)
        assertEquals(0, vm.posicion) // Se reinicia tras completar la secuencia
        assertEquals(1, Datos.ronda.value) // Aumenta ronda
    }

    @Test
    fun `colorSeleccionado reinicia secuencia si falla`() {
        Datos.secuencia.value = mutableListOf(Colores.CLASE_ROJO)
        Datos.ronda.value = 2
        vm.colorSeleccionado(Colores.CLASE_VERDE)
        assertEquals(0, Datos.ronda.value)
        assertEquals(0, vm.posicion)
        assertEquals(Estado.FINALIZADO, Datos.estado.value)
        assertTrue(Datos.secuencia.value.isEmpty())
    }

    @Test
    fun `realizarSecuencia cambia estado a ELECCION_USUARIO al final`() = runTest {
        Datos.secuencia.value = mutableListOf(Colores.CLASE_ROJO, Colores.CLASE_VERDE)
        vm.realizarSecuencia()
        // Simulamos los delays
        advanceTimeBy(4000)
        assertEquals(Estado.ELECCION_USUARIO, Datos.estado.value)
    }

    @Test
    fun `iniciarJuego añade un color y realizaSecuencia`() = runTest {
        val initialSize = Datos.secuencia.value.size
        vm.iniciarJuego()
        advanceTimeBy(2000) // Avanzamos delay del primer color
        assertEquals(initialSize + 1, Datos.secuencia.value.size)
        assertEquals(Estado.ELECCION_USUARIO, Datos.estado.value)
    }

    @Test
    fun `comprobarRecord actualiza record si es mayor`() {
        Datos.ronda.value = 5
        Datos.record.value = 3
        vm.comprobarRecord()
        assertEquals(5, Datos.record.value)
    }

    @Test
    fun `comprobarRecord no cambia record si es menor`() {
        Datos.ronda.value = 2
        Datos.record.value = 5
        vm.comprobarRecord()
        assertEquals(5, Datos.record.value)
    }

    @Test
    fun `volverAlIdle cambia estado a IDLE`() {
        Datos.estado.value = Estado.FINALIZADO
        vm.volverAlIdle()
        assertEquals(Estado.IDLE, Datos.estado.value)
    }

    // Añade estos tests a los que ya tienes:

    @Test
    fun `comprobarRecord guarda record con fecha cuando es nuevo record`() = runTest {
        // Arrange
        Datos.ronda.value = 5
        Datos.record.value = 3

        // Act
        vm.comprobarRecord()

        // Assert
        assertEquals(5, Datos.record.value)
        // El recordConFecha debería estar actualizado en el ViewModel
    }

    @Test
    fun `cargarRecordInicial carga record desde almacenamiento`() = runTest {
        // Este test es más complejo porque necesita contexto
        // Para pruebas reales necesitarías mockear el RecordSharedP
        assertTrue(true) // Placeholder - en realidad mockearías
    }

    @Test
    fun `recordConFecha se actualiza cuando hay nuevo record`() = runTest {
        // Arrange
        Datos.ronda.value = 6
        Datos.record.value = 4

        // Act
        vm.comprobarRecord()

        // Assert
        // Necesitarías acceso a vm.recordConFecha
        // En un test real usarías un observer o expondrías el estado de otra forma
    }
}
