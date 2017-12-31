package io.fotoapparat.hardware.orientation

import org.junit.Test
import kotlin.test.assertEquals

class OrientationTest {

    @Test
    fun `When angle is 5, return 0`() {
        assertEquals(
                expected = 0,
                actual = toClosestRightAngle(5)
        )
    }

    @Test
    fun `When angle is 60, return 90`() {
        assertEquals(
                expected = 90,
                actual = toClosestRightAngle(60)
        )
    }

    @Test
    fun `When angle is 190, return 180`() {
        assertEquals(
                180,
                toClosestRightAngle(190)
        )
    }

    @Test
    fun `When angle is 269, return 270`() {
        assertEquals(
                270,
                toClosestRightAngle(269)
        )
    }

    @Test
    fun `When angle is 359, return 0`() {
        assertEquals(
                expected = 0,
                actual = toClosestRightAngle(359)
        )
    }


    @Test
    fun `Portait & front camera at 270 degrees return 90 display orientation`() {
        // Given
        val screenOrientation = 0
        val cameraOrientation = 270

        // When
        val result = computeDisplayOrientation(
                screenOrientation,
                cameraOrientation,
                true
        )

        // Then
        assertEquals(90, result)
    }

    @Test
    fun `Landscape & front camera at 270 degrees return 180 display orientation`() {
        // Given
        val screenOrientation = 270
        val cameraOrientation = 270

        // When
        val result = computeDisplayOrientation(
                screenOrientation,
                cameraOrientation,
                true
        )

        // Then
        assertEquals(180, result)
    }

    @Test
    fun `Portrait & back camera at 90 degrees return 90 display orientation`() {
        // Given
        val screenOrientation = 0
        val cameraOrientation = 90

        // When
        val result = computeDisplayOrientation(
                screenOrientation,
                cameraOrientation,
                false
        )

        // Then
        assertEquals(90, result)
    }

    @Test
    fun `Landscape & back camera at 90 degrees return 180 display orientation`() {
        // Given
        val screenOrientation = 270
        val cameraOrientation = 90

        // When
        val result = computeDisplayOrientation(
                screenOrientation,
                cameraOrientation,
                false
        )

        // Then
        assertEquals(180, result)
    }

    @Test
    fun `Portrait & front camera at 270 degrees return 90 image orientation`() {
        // Given
        val screenOrientation = 0
        val cameraOrientation = 270

        // When
        val result = computeImageOrientation(
                screenOrientation,
                cameraOrientation,
                true
        )

        // Then
        assertEquals(90, result)
    }

    @Test
    fun `Landscape & front camera at 270 degrees return 180 image orientation`() {
        // Given
        val screenOrientation = 270
        val cameraOrientation = 270

        // When
        val result = computeImageOrientation(
                screenOrientation,
                cameraOrientation,
                true
        )

        // Then
        assertEquals(180, result)
    }

    @Test
    fun `Portrait & back camera at 90 degrees return 270 image orientation`() {
        // Given
        val screenOrientation = 0
        val cameraOrientation = 90

        // When
        val result = computeImageOrientation(
                screenOrientation,
                cameraOrientation,
                false
        )

        // Then
        assertEquals(270, result)
    }

    @Test
    fun `Landscape & back camera at 90 degrees return 180 image orientation`() {
        // Given
        val screenOrientation = 270
        val cameraOrientation = 90

        // When
        val result = computeImageOrientation(
                screenOrientation,
                cameraOrientation,
                false
        )

        // Then
        assertEquals(180, result)
    }

}