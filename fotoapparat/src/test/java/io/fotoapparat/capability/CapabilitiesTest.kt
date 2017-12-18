package io.fotoapparat.capability

import io.fotoapparat.test.testCapabilities
import org.junit.Test
import kotlin.test.assertEquals

class CapabilitiesTest {

    val validCapabilities = testCapabilities

    @Test(expected = IllegalArgumentException::class)
    fun `Ensure flash modes not empty`() {
        // When
        validCapabilities.copy(
                flashModes = emptySet()
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Ensure focus modes not empty`() {
        // When
        validCapabilities.copy(
                focusModes = emptySet()
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Ensure preview fps ranges not empty`() {
        // When
        validCapabilities.copy(
                previewFpsRanges = emptySet()
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Ensure picture resolutions not empty`() {
        // When
        validCapabilities.copy(
                pictureResolutions = emptySet()
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Ensure preview resolutions not empty`() {
        // When
        validCapabilities.copy(
                previewResolutions = emptySet()
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Ensure autoBanding modes not empty`() {
        // When
        validCapabilities.copy(
                antiBandingModes = emptySet()
        )
    }

    @Test
    fun `Ensure sensor sensitivities can be empty`() {
        // When
        val capabilities = validCapabilities.copy(
                sensorSensitivities = emptySet()
        )

        // Then
        assertEquals(
                expected = emptySet(),
                actual = capabilities.sensorSensitivities
        )
    }

}