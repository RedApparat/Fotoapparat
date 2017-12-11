package io.fotoapparat.capability

import io.fotoapparat.parameter.Flash
import io.fotoapparat.parameter.FocusMode
import io.fotoapparat.parameter.FpsRange
import io.fotoapparat.parameter.Resolution
import org.junit.Test
import kotlin.test.assertEquals

class CapabilitiesTest {

    val resolution = Resolution(10, 10)

    val validCapabilities = Capabilities(
            canZoom = false,
            flashModes = setOf(Flash.AutoRedEye),
            focusModes = setOf(FocusMode.Fixed),
            canSmoothZoom = false,
            previewFpsRanges = setOf(FpsRange(20000, 20000)),
            pictureResolutions = setOf(resolution),
            previewResolutions = setOf(resolution),
            sensorSensitivities = setOf(100)
    )

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