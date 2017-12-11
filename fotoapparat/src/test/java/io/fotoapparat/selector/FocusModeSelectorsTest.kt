package io.fotoapparat.selector

import io.fotoapparat.parameter.FocusMode
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FocusModeSelectorsTest {

    @Test
    fun `Select continuous focus which is available`() {
        // Given
        val availableModes = listOf(
                FocusMode.Auto,
                FocusMode.ContinuousFocusPicture,
                FocusMode.Fixed
        )

        // When
        val result = continuousFocusPicture()(availableModes)

        // Then
        assertEquals(
                expected = FocusMode.ContinuousFocusPicture,
                actual = result
        )
    }

    @Test
    fun `Select continuous focus which is not available`() {
        // Given
        val availableModes = listOf(
                FocusMode.Auto,
                FocusMode.Fixed
        )

        // When
        val result = continuousFocusPicture()(availableModes)

        // Then
        assertNull(result)
    }

}