package io.fotoapparat.selector

import io.fotoapparat.characteristic.LensPosition
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LensPositionSelectorsTest {

    @Test
    fun `Select front camera which is available`() {
        // Given
        val availablePositions = listOf(
                LensPosition.Back,
                LensPosition.Front,
                LensPosition.External
        )

        // When
        val result = front()(availablePositions)

        // Then
        assertEquals(
                LensPosition.Front,
                result
        )
    }

    @Test
    fun `Select front camera which is not available`() {
        // Given
        val availablePositions = listOf(
                LensPosition.Back,
                LensPosition.External
        )

        // When
        val result = front()(availablePositions)

        // Then
        assertNull(result)
    }

    @Test
    fun `Select back camera which is available`() {
        // Given
        val availablePositions = listOf(
                LensPosition.Back,
                LensPosition.Front,
                LensPosition.External
        )

        // When
        val result = back()(availablePositions)

        // Then
        assertEquals(
                LensPosition.Back,
                result
        )
    }

    @Test
    fun `Select back camera which is not available`() {
        // Given
        val availablePositions = listOf(
                LensPosition.Front,
                LensPosition.External
        )

        // When
        val result = back()(availablePositions)

        // Then
        assertNull(result)
    }

    @Test
    fun `Select external camera which is available`() {
        // Given
        val availablePositions = listOf(
                LensPosition.Back,
                LensPosition.Front,
                LensPosition.External
        )

        // When
        val result = external()(availablePositions)

        // Then
        assertEquals(
                LensPosition.External,
                result
        )
    }

    @Test
    fun `Select external camera which is not available`() {
        // Given
        val availablePositions = listOf(
                LensPosition.Front,
                LensPosition.Back
        )

        // When
        val result = external()(availablePositions)

        // Then
        assertNull(result)
    }

}