package io.fotoapparat.parameter

import org.junit.Test
import kotlin.test.assertEquals

class ResolutionTest {

    @Test
    fun `Get area`() {
        assertEquals(
                250 * 100,
                Resolution(250, 100).area
        )
    }

    @Test
    fun `Get aspect ratio`() {
        assertEquals(
                2.5f,
                Resolution(250, 100).aspectRatio
        )
    }

    @Test
    fun `Get aspect ratio for empty resolution`() {
        assertEquals(
                Float.NaN,
                Resolution(0, 0).aspectRatio
        )
    }

    @Test
    fun `Flip dimensions`() {
        assertEquals(
                Resolution(10, 20),
                Resolution(20, 10).flipDimensions()
        )
    }

}