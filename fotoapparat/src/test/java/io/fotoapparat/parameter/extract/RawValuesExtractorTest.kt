package io.fotoapparat.parameter.extract

import android.hardware.Camera
import io.fotoapparat.test.willReturn
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class RawValuesExtractorTest {

    @Mock
    lateinit var parameters: Camera.Parameters

    @Test
    fun `Extract non available values`() {
        // Given
        val key = "effect-values"

        // When
        val values = parameters.extractRawCameraValues(listOf(key))

        // Then
        assertEquals(
                expected = emptyList(),
                actual = values
        )
    }

    @Test
    fun `Extract available values`() {
        // Given
        val key = "effect-values"
        parameters.get(key) willReturn "none,mono,negative,solarize"

        // When
        val values = parameters.extractRawCameraValues(listOf(key))

        // Then
        assertEquals(
                expected = listOf(
                        "none",
                        "mono",
                        "negative",
                        "solarize"
                ),
                actual = values
        )
    }

}