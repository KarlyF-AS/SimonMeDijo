package com.dam.simonmedijo

import org.junit.Assert.*
import org.junit.Test
import java.util.Date

/**
 * Tests para la clase Record.
 */
class RecordTest {

    @Test
    fun `crearDesdeRonda genera fecha correctamente`() {
        // Arrange
        val ronda = 7
        val fechaAntes = Date().time

        // Act
        val record = Record.crearDesdeRonda(ronda)
        val fechaDespues = Date().time

        // Assert
        assertEquals(ronda, record.maxRonda)
        assertTrue(record.fechaTexto.isNotBlank())
        assertTrue(record.tiempoMS in fechaAntes..fechaDespues)
    }

    @Test
    fun `Record con mismos valores debe ser igual`() {
        val record1 = Record(5, "15/12/2024 18:30", 1734273000000)
        val record2 = Record(5, "15/12/2024 18:30", 1734273000000)

        assertEquals(record1, record2)
        assertEquals(record1.hashCode(), record2.hashCode())
    }

    @Test
    fun `Record con diferentes valores no debe ser igual`() {
        val record1 = Record(5, "15/12/2024 18:30", 1734273000000)
        val record2 = Record(6, "15/12/2024 18:30", 1734273000000)

        assertNotEquals(record1, record2)
    }

    @Test
    fun `toString muestra informacion relevante`() {
        val record = Record(3, "15/12/2024 18:30", 1734273000000)
        val str = record.toString()

        assertTrue(str.contains("3"))
        assertTrue(str.contains("15/12/2024"))
    }
}