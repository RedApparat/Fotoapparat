package io.fotoapparat.selector

import io.fotoapparat.parameter.Resolution
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ResolutionSelectorsTest {

    @Test
    fun `Select highest resolution`() {
        // Given
        val availableResolutions = listOf(
                Resolution(4032, 3024),
                Resolution(7680, 4320),
                Resolution(1280, 720)
        )

        // When
        val result = highestResolution()(availableResolutions)

        // Then
        assertEquals(
                Resolution(7680, 4320),
                result
        )
    }

    @Test
    fun `Select highest resolution, but with empty list return null`() {
        // When
        val result = highestResolution()(emptyList())

        // Then
        assertNull(result)
    }

    @Test
    fun `Select lowest resolution`() {
        // Given
        val availableResolutions = listOf(
                Resolution(4032, 3024),
                Resolution(7680, 4320),
                Resolution(1280, 720)
        )

        // When
        val result = lowestResolution()(availableResolutions)

        // Then
        assertEquals(
                Resolution(1280, 720),
                result
        )
    }

    @Test
    fun `Select lowest resolution, but with empty list return null`() {
        // When
        val result = lowestResolution()(emptyList())

        // Then
        assertNull(result)
    }


}