package io.fotoapparat.selector

import io.fotoapparat.parameter.Resolution
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class AspectRatioSelectorsTest {

    var receivedResolutions: Iterable<Resolution>? = null

    @Before
    fun setUp() {
        receivedResolutions = null
    }

    @Test
    fun `Filter standard ratios`() {
        // Given
        val sizeSelector: Iterable<Resolution>.() -> Resolution = {
            receivedResolutions = this
            Resolution(4, 3)
        }

        // When
        val result = standardRatio(selector = sizeSelector)(listOf(
                Resolution(4, 3),
                Resolution(8, 6),
                Resolution(10, 10)
        ))

        // Then
        assertEquals(
                expected = Resolution(4, 3),
                actual = result
        )

        assertEquals(
                expected = listOf(
                        Resolution(4, 3),
                        Resolution(8, 6)
                ),
                actual = receivedResolutions
        )
    }

    @Test
    fun `Filter standard ratios with tolerance`() {
        // Given
        val sizeSelector: Iterable<Resolution>.() -> Resolution = {
            receivedResolutions = this
            Resolution(400, 300)
        }

        // When
        val result = standardRatio(
                selector = sizeSelector,
                tolerance = 0.1
        )(listOf(
                Resolution(400, 300),
                Resolution(410, 300),
                Resolution(800, 600),
                Resolution(790, 600),
                Resolution(100, 100),
                Resolution(160, 90)
        ))

        // Then
        assertEquals(
                expected = Resolution(400, 300),
                actual = result
        )

        assertEquals(
                expected = listOf(
                        Resolution(400, 300),
                        Resolution(410, 300),
                        Resolution(800, 600),
                        Resolution(790, 600)
                ),
                actual = receivedResolutions
        )
    }

    @Test
    fun `Filter wide ratios`() {
        // Given
        val sizeSelector: Iterable<Resolution>.() -> Resolution = {
            receivedResolutions = this
            Resolution(16, 9)
        }

        // When
        val result = wideRatio(selector = sizeSelector)(listOf(
                Resolution(16, 9),
                Resolution(32, 18),
                Resolution(10, 10)
        ))

        // Then
        assertEquals(
                expected = Resolution(16, 9),
                actual = result
        )

        assertEquals(
                expected = listOf(
                        Resolution(16, 9),
                        Resolution(32, 18)
                ),
                actual = receivedResolutions
        )
    }

    @Test
    fun `Filter wide ratios with tolerance`() {
        // Given
        val sizeSelector: Iterable<Resolution>.() -> Resolution = {
            receivedResolutions = this
            Resolution(16, 9)
        }

        // When
        val result = wideRatio(
                selector = sizeSelector,
                tolerance = 0.1
        )(listOf(
                Resolution(16, 9),
                Resolution(16, 10),
                Resolution(32, 18),
                Resolution(32, 20),
                Resolution(10, 10)
        ))

        // Then
        assertEquals(
                expected = Resolution(16, 9),
                actual = result
        )

        assertEquals(
                expected = listOf(
                        Resolution(16, 9),
                        Resolution(16, 10),
                        Resolution(32, 18),
                        Resolution(32, 20)
                ),
                actual = receivedResolutions
        )
    }

    @Test
    fun `Filter custom aspect ratio with tolerance`() {
        // Given
        val sizeSelector: Iterable<Resolution>.() -> Resolution = {
            receivedResolutions = this
            Resolution(110, 100)
        }

        // When
        val result = aspectRatio(
                aspectRatio = 1.0f,
                selector = sizeSelector,
                tolerance = 0.1
        )(listOf(
                Resolution(16, 9),
                Resolution(110, 100),
                Resolution(105, 100),
                Resolution(95, 100),
                Resolution(90, 100),
                Resolution(4, 3),
                Resolution(20, 10)
        ))

        // Then
        assertEquals(
                expected = Resolution(110, 100),
                actual = result
        )

        assertEquals(
                expected = listOf(
                        Resolution(110, 100),
                        Resolution(105, 100),
                        Resolution(95, 100),
                        Resolution(90, 100)
                ),
                actual = receivedResolutions
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Custom Ratio with tolerance less than 0 should throw exception`() {
        // Given
        val sizeSelector: Iterable<Resolution>.() -> Resolution = {
            Resolution(16, 9)
        }

        // When
        aspectRatio(
                aspectRatio = 1.0f,
                selector = sizeSelector,
                tolerance = -0.1
        )(listOf(
                Resolution(16, 9),
                Resolution(16, 10)
        ))

        // Then exception is thrown
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Custom ratio with tolerance above 1 should throw exception`() {
        // Given
        val sizeSelector: Iterable<Resolution>.() -> Resolution = {
            Resolution(16, 9)
        }

        // When
        aspectRatio(
                aspectRatio = 1.0f,
                selector = sizeSelector,
                tolerance = 1.1
        )(listOf(
                Resolution(16, 9),
                Resolution(16, 10)
        ))

        // Then exception is thrown
    }
}
