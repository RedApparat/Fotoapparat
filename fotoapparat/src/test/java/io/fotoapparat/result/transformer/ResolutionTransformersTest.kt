package io.fotoapparat.result.transformer

import io.fotoapparat.parameter.Resolution
import org.junit.Test
import kotlin.test.assertEquals

class ResolutionTransformersTest {

    @Test
    fun `Original size`() {
        assertEquals(
                Resolution(100, 200),
                originalResolution()(Resolution(100, 200))
        )
    }

    @Test
    fun `Scaled size`() {
        assertEquals(
                Resolution(50, 100),
                scaled(scaleFactor = 0.5f)(Resolution(100, 200))
        )
    }
}