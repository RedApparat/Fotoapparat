package io.fotoapparat.hardware.orientation

import org.junit.Test
import kotlin.test.assertEquals

class RotationTest {

    @Test
    fun `When angle is 5, return 0`() {
        assertEquals(
                expected = 0,
                actual = 5.toClosestRightAngle()
        )
    }

    @Test
    fun `When angle is 60, return 90`() {
        assertEquals(
                expected = 90,
                actual = 60.toClosestRightAngle()
        )
    }

    @Test
    fun `When angle is 190, return 180`() {
        assertEquals(
                180,
                190.toClosestRightAngle()
        )
    }

    @Test
    fun `When angle is 269, return 270`() {
        assertEquals(
                270,
                269.toClosestRightAngle()
        )
    }

    @Test
    fun `When angle is 359, return 0`() {
        assertEquals(
                expected = 0,
                actual = 359.toClosestRightAngle()
        )
    }

}