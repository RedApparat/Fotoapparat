package io.fotoapparat.parameter.camera.convert

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertNull

@RunWith(MockitoJUnitRunner::class)
class FlashConverterTest {

    @Test
    fun `Samsung Grand Prime has also manual focus mode, we return null and ignore it`() {
        // When
        val result = "manual".toFlash()

        // Then
        assertNull(result)
    }
}