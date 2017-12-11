package io.fotoapparat.selector

import io.fotoapparat.parameter.FpsRange
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PreviewFpsRangeSelectorsTest {

    @Test
    fun `Select fps range by exact fps value which is available`() {
        // Given
        val availableRanges = listOf(
                FpsRange(24000, 24000),
                FpsRange(30000, 30000),
                FpsRange(40000, 40000)
        )

        // When
        val result = exactFixedFps(30f)(availableRanges)

        // Then
        assertEquals(
                expected = FpsRange(30000, 30000),
                actual = result
        )
    }

    @Test
    fun `Select fps range by exact fps value which is not available`() {
        // Given
        val availableRanges = listOf(
                FpsRange(24000, 30000),
                FpsRange(30000, 36000),
                FpsRange(40000, 40000)
        )

        // When
        val result = exactFixedFps(30f)(availableRanges)

        // Then
        assertNull(result)
    }

    @Test
    fun `Select fps range which contains fps and fixed value is available`() {
        // Given
        val availableRanges = listOf(
                FpsRange(24000, 30000),
                FpsRange(30000, 30000),
                FpsRange(30000, 34000),
                FpsRange(30000, 36000)
        )

        // When
        val result = containsFps(30f)(availableRanges)

        // Then
        assertEquals(
                expected = FpsRange(30000, 30000),
                actual = result
        )
    }

    @Test
    fun `Select fps range which contains fps and not-fixed value is available`() {
        // Given
        val availableRanges = listOf(
                FpsRange(24000, 30000),
                FpsRange(30000, 34000),
                FpsRange(30000, 36000)
        )

        // When
        val result = containsFps(30f)(availableRanges)

        // Then
        assertEquals(
                expected = FpsRange(30000, 36000),
                actual = result
        )
    }

    @Test
    fun `Select highest fps`() {
        // Given
        val availableRanges = listOf(
                FpsRange(24000, 24000),
                FpsRange(24000, 30000),
                FpsRange(30000, 34000),
                FpsRange(30000, 36000),
                FpsRange(36000, 36000)
        )

        // When
        val result = highestFps()(availableRanges)

        // Then
        assertEquals(
                expected = FpsRange(30000, 36000),
                actual = result
        )
    }

    @Test
    fun `Select lowest fps`() {
        // Given
        val availableRanges = listOf(
                FpsRange(24000, 24000),
                FpsRange(24000, 30000),
                FpsRange(30000, 34000),
                FpsRange(30000, 36000),
                FpsRange(36000, 36000)
        )

        // When
        val result = lowestFps()(availableRanges)

        // Then
        assertEquals(
                expected = FpsRange(24000, 30000),
                actual = result
        )
    }

    @Test
    fun `Select highest non-fixed fps`() {
        // Given
        val availableRanges = listOf(
                FpsRange(24000, 24000),
                FpsRange(24000, 30000),
                FpsRange(30000, 34000),
                FpsRange(30000, 36000),
                FpsRange(36000, 36000)
        )

        // When
        val result = highestNonFixedFps()(availableRanges)

        // Then
        assertEquals(
                expected = FpsRange(30000, 36000),
                actual = result
        )
    }

    @Test
    fun `Select lowest non-fixed fps`() {
        // Given
        val availableRanges = listOf(
                FpsRange(24000, 24000),
                FpsRange(24000, 30000),
                FpsRange(30000, 34000),
                FpsRange(30000, 36000),
                FpsRange(36000, 36000)
        )

        // When
        val result = lowestNonFixedFps()(availableRanges)

        // Then
        assertEquals(
                expected = FpsRange(24000, 30000),
                actual = result
        )
    }

    @Test
    fun `Select highest fixed fps`() {
        // Given
        val availableRanges = listOf(
                FpsRange(24000, 24000),
                FpsRange(24000, 30000),
                FpsRange(30000, 34000),
                FpsRange(30000, 36000),
                FpsRange(36000, 36000)
        )

        // When
        val result = highestFixedFps()(availableRanges)

        // Then
        assertEquals(
                expected = FpsRange(36000, 36000),
                actual = result
        )
    }

    @Test
    fun `Select lowest fixed fps`() {
        // Given
        val availableRanges = listOf(
                FpsRange(24000, 24000),
                FpsRange(24000, 30000),
                FpsRange(30000, 34000),
                FpsRange(30000, 36000),
                FpsRange(36000, 36000)
        )

        // When
        val result = lowestFixedFps()(availableRanges)

        // Then
        assertEquals(
                expected = FpsRange(24000, 24000),
                actual = result
        )
    }

}