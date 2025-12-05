package com.dam.simonmedijo

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Tests para RecordSharedP que implementa HistorialRecord.
 *
 * @see https://developer.android.com/training/testing/local-tests
 */
class RecordSharedPTest {

    private lateinit var context: Context
    private lateinit var recordStorage: RecordSharedP

    @Before
    fun setup() {
        // Contexto de prueba
        context = ApplicationProvider.getApplicationContext()
        recordStorage = RecordSharedP(context)

        // Limpiar SharedPreferences antes de cada test
        val prefs = context.getSharedPreferences(
            "com.dam.simonmedijo.RECORD_PREFS",
            Context.MODE_PRIVATE
        )
        prefs.edit().clear().apply()
    }

    /**
     * Test que verifica que se puede guardar y cargar un Record correctamente.
     */
    @Test
    fun `guardar y cargar record funciona correctamente`() = runTest {
        // 1. Crear un record de prueba
        val record = Record.crearDesdeRonda(5)

        // 2. Guardarlo
        recordStorage.guardarRecord(record)

        // 3. Cargarlo
        val recordCargado = recordStorage.cargarRecord()

        // 4. Verificar que es el mismo
        assertNotNull("El record cargado no debería ser null", recordCargado)
        assertEquals("El número de ronda no coincide", 5, recordCargado?.maxRonda)
        assertEquals("La fecha en texto no coincide", record.fechaTexto, recordCargado?.fechaTexto)
        assertEquals("El timestamp no coincide", record.tiempoMS, recordCargado?.tiempoMS)
    }

    /**
     * Test que verifica que cargar un record inexistente devuelve null.
     */
    @Test
    fun `cargar record inexistente devuelve null`() = runTest {
        // Cuando no hay record guardado, debe devolver null
        val recordCargado = recordStorage.cargarRecord()

        assertNull("Debería devolver null cuando no hay record", recordCargado)
    }

    /**
     * Test que verifica que se puede actualizar un record existente.
     */
    @Test
    fun `actualizar record funciona correctamente`() = runTest {
        // 1. Guardar primer record
        val record1 = Record.crearDesdeRonda(3)
        recordStorage.guardarRecord(record1)

        // 2. Guardar segundo record (debería sobreescribir)
        val record2 = Record.crearDesdeRonda(7)
        recordStorage.guardarRecord(record2)

        // 3. Cargar - debe ser el segundo
        val recordCargado = recordStorage.cargarRecord()

        assertEquals("Debería haber guardado el último record", 7, recordCargado?.maxRonda)
    }

    /**
     * Test que verifica obtenerRondaRecord devuelve el valor correcto.
     */
    @Test
    fun `obtenerRondaRecord devuelve valor correcto`() = runTest {
        // 1. Sin record guardado, debería devolver 0
        assertEquals(0, recordStorage.obtenerRondaRecord())

        // 2. Guardar un record
        val record = Record.crearDesdeRonda(8)
        recordStorage.guardarRecord(record)

        // 3. Debería devolver 8
        assertEquals(8, recordStorage.obtenerRondaRecord())
    }

    /**
     * Test que verifica que los datos persisten entre instancias.
     */
    @Test
    fun `los datos persisten entre diferentes instancias`() = runTest {
        // 1. Guardar con una instancia
        val record = Record.crearDesdeRonda(10)
        recordStorage.guardarRecord(record)

        // 2. Crear NUEVA instancia
        val nuevaInstancia = RecordSharedP(context)

        // 3. Cargar con la nueva instancia
        val recordCargado = nuevaInstancia.cargarRecord()

        // 4. Debería ser el mismo record
        assertNotNull(recordCargado)
        assertEquals(10, recordCargado?.maxRonda)
    }
}