package io.fotoapparat.parameter

import org.junit.Test
import kotlin.test.assertTrue

class FpsRangeTest {

    @Test
    fun `Is fixed`() {
        // When
        val result = FpsRange(30000, 30000).isFixed

        // Then
        assertTrue(result)
    }
}