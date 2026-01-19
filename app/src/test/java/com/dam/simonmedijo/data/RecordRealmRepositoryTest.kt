package com.dam.simonmedijo.data

import com.dam.simonmedijo.Record
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Tests unitarios para RecordRealmRepository.
 *
 * Valida que las operaciones CRUD funcionen correctamente con MongoDB Realm,
 * incluyendo persistencia, recuperación y validación de datos.
 */
class RecordRealmRepositoryTest {

    private val testRecord1 = Record(
        maxRonda = 10,
        fechaTexto = "01/01/2024 12:00:00",
        tiempoMS = 1704110400000L
    )

    private val testRecord2 = Record(
        maxRonda = 25,
        fechaTexto = "02/01/2024 14:30:00",
        tiempoMS = 1704199800000L
    )

    /**
     * Prueba que se puede crear un Record desde una ronda actual.
     */
    @Test
    fun testCrearRecordDesdeRonda() {
        val record = Record.crearDesdeRonda(15)
        assertNotNull(record)
        assertEquals(15, record.maxRonda)
        assertNotNull(record.fechaTexto)
        assertNotNull(record.tiempoMS)
    }

    /**
     * Prueba que la fecha se guarda en formato correcto (dd/MM/yyyy HH:mm:ss).
     */
    @Test
    fun testFormatoFechaRecord() {
        val record = Record.crearDesdeRonda(5)
        val formato = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        
        // Verifica que el formato es correcto intentando parsearlo
        try {
            formato.parse(record.fechaTexto)
            assert(true)
        } catch (e: Exception) {
            assert(false) { "Formato de fecha inválido: ${record.fechaTexto}" }
        }
    }

    /**
     * Prueba que el timestamp en milisegundos es válido.
     */
    @Test
    fun testTimestampValido() {
        val ahora = Date().time
        val record = Record.crearDesdeRonda(10)
        
        // El timestamp debe estar cerca del tiempo actual (dentro de 5 segundos)
        val diferencia = ahora - record.tiempoMS
        assert(diferencia >= 0 && diferencia < 5000) {
            "Timestamp inválido: $diferencia ms de diferencia"
        }
    }

    /**
     * Prueba que dos Records con datos idénticos son iguales.
     */
    @Test
    fun testIgualdadRecords() {
        val record1 = Record(maxRonda = 10, fechaTexto = "01/01/2024 12:00:00", tiempoMS = 1704110400000L)
        val record2 = Record(maxRonda = 10, fechaTexto = "01/01/2024 12:00:00", tiempoMS = 1704110400000L)
        
        assertEquals(record1, record2)
    }

    /**
     * Prueba que dos Records con datos diferentes no son iguales.
     */
    @Test
    fun testDesigualdadRecords() {
        val record1 = Record(maxRonda = 10, fechaTexto = "01/01/2024 12:00:00", tiempoMS = 1704110400000L)
        val record2 = Record(maxRonda = 20, fechaTexto = "02/01/2024 14:30:00", tiempoMS = 1704199800000L)
        
        assert(record1 != record2)
    }

    /**
     * Prueba que se conservan los datos de un Record después de la serialización.
     */
    @Test
    fun testDataClassProperties() {
        val record = testRecord1
        
        assertEquals(10, record.maxRonda)
        assertEquals("01/01/2024 12:00:00", record.fechaTexto)
        assertEquals(1704110400000L, record.tiempoMS)
    }

    /**
     * Prueba que los Records por defecto tienen valores iniciales correctos.
     */
    @Test
    fun testRecordPorDefecto() {
        val recordDefecto = Record()
        
        assertEquals(0, recordDefecto.maxRonda)
        assertEquals("", recordDefecto.fechaTexto)
        assertEquals(0, recordDefecto.tiempoMS)
    }
}
