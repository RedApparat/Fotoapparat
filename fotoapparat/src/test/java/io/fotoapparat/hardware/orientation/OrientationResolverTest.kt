package io.fotoapparat.hardware.orientation

import io.fotoapparat.hardware.orientation.Orientation.Horizontal.Landscape
import io.fotoapparat.hardware.orientation.Orientation.Horizontal.ReverseLandscape
import io.fotoapparat.hardware.orientation.Orientation.Vertical.Portrait
import io.fotoapparat.hardware.orientation.Orientation.Vertical.ReversePortrait
import org.junit.Test
import kotlin.test.assertEquals

class OrientationResolverTest {

    @Test
    fun `Portait & front camera at 270 degrees return 90 display orientation`() {
        // Given
        val screenOrientation = Portrait
        val cameraOrientation = ReverseLandscape

        // When
        val result = computeDisplayOrientation(
                screenOrientation,
                cameraOrientation,
                true
        )

        // Then
        assertEquals(Landscape, result)
    }

    @Test
    fun `Landscape & front camera at 270 degrees return 180 display orientation`() {
        // Given
        val screenOrientation = ReverseLandscape
        val cameraOrientation = ReverseLandscape

        // When
        val result = computeDisplayOrientation(
                screenOrientation,
                cameraOrientation,
                true
        )

        // Then
        assertEquals(ReversePortrait, result)
    }

    @Test
    fun `Portrait & back camera at 90 degrees return 90 display orientation`() {
        // Given
        val screenOrientation = Portrait
        val cameraOrientation = Landscape

        // When
        val result = computeDisplayOrientation(
                screenOrientation,
                cameraOrientation,
                false
        )

        // Then
        assertEquals(Landscape, result)
    }

    @Test
    fun `Landscape & back camera at 90 degrees return 180 display orientation`() {
        // Given
        val screenOrientation = ReverseLandscape
        val cameraOrientation = Landscape

        // When
        val result = computeDisplayOrientation(
                screenOrientation,
                cameraOrientation,
                false
        )

        // Then
        assertEquals(ReversePortrait, result)
    }

    @Test
    fun `Portrait & front camera at 270 degrees return 90 preview orientation`() {
        // Given
        val screenOrientation = Portrait
        val cameraOrientation = ReverseLandscape

        // When
        val result = computePreviewOrientation(
                screenOrientation,
                cameraOrientation,
                true
        )

        // Then
        assertEquals(Landscape, result)
    }

    @Test
    fun `Landscape & front camera at 270 degrees return 180 preview orientation`() {
        // Given
        val screenOrientation = ReverseLandscape
        val cameraOrientation = ReverseLandscape

        // When
        val result = computePreviewOrientation(
                screenOrientation,
                cameraOrientation,
                true
        )

        // Then
        assertEquals(ReversePortrait, result)
    }

    @Test
    fun `Portrait & back camera at 90 degrees return 270 preview orientation`() {
        // Given
        val screenOrientation = Portrait
        val cameraOrientation = Landscape

        // When
        val result = computePreviewOrientation(
                screenOrientation,
                cameraOrientation,
                false
        )

        // Then
        assertEquals(ReverseLandscape, result)
    }

    @Test
    fun `Landscape & back camera at 90 degrees return 180 preview orientation`() {
        // Given
        val screenOrientation = ReverseLandscape
        val cameraOrientation = Landscape

        // When
        val result = computePreviewOrientation(
                screenOrientation,
                cameraOrientation,
                false
        )

        // Then
        assertEquals(ReversePortrait, result)
    }


    @Test
    fun `Portrait & front camera at 270 degrees return 90 image orientation`() {
        // Given
        val screenOrientation = Portrait
        val cameraOrientation = ReverseLandscape

        // When
        val result = computeImageOrientation(
                screenOrientation,
                cameraOrientation,
                true
        )

        // Then
        assertEquals(Landscape, result)
    }

    @Test
    fun `Landscape & front camera at 270 degrees return 0 image orientation`() {
        // Given
        val screenOrientation = ReverseLandscape
        val cameraOrientation = ReverseLandscape

        // When
        val result = computeImageOrientation(
                screenOrientation,
                cameraOrientation,
                true
        )

        // Then
        assertEquals(Portrait, result)
    }

    @Test
    fun `Portrait & back camera at 90 degrees return 270 image orientation`() {
        // Given
        val screenOrientation = Portrait
        val cameraOrientation = Landscape

        // When
        val result = computeImageOrientation(
                screenOrientation,
                cameraOrientation,
                false
        )

        // Then
        assertEquals(ReverseLandscape, result)
    }

    @Test
    fun `Landscape & back camera at 90 degrees return 180 image orientation`() {
        // Given
        val screenOrientation = ReverseLandscape
        val cameraOrientation = Landscape

        // When
        val result = computeImageOrientation(
                screenOrientation,
                cameraOrientation,
                false
        )

        // Then
        assertEquals(Portrait, result)
    }


}