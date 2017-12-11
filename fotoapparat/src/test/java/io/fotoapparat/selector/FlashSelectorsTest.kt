package io.fotoapparat.selector

import io.fotoapparat.parameter.Flash
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FlashSelectorsTest {

    @Test
    fun `Select auto red eye which is available`() {
        // Given
        val availableModes = listOf(
                Flash.Auto,
                Flash.AutoRedEye,
                Flash.Off
        )

        // When
        val result = autoRedEye()(availableModes)

        // Then
        assertEquals(
                expected = Flash.AutoRedEye,
                actual = result
        )
    }

    @Test
    fun `Select auto red eye which is not available`() {
        // Given
        val availableModes = listOf(
                Flash.Auto,
                Flash.Off
        )

        // When
        val result = autoRedEye()(availableModes)

        // Then
        assertNull(result)
    }


}