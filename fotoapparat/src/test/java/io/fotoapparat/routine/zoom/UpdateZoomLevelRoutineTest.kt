package io.fotoapparat.routine.zoom

import io.fotoapparat.exception.LevelOutOfRangeException
import io.fotoapparat.hardware.CameraDevice
import io.fotoapparat.hardware.Device
import io.fotoapparat.test.testCapabilities
import io.fotoapparat.test.willReturn
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyFloat
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class UpdateZoomLevelRoutineTest {

    @Mock
    lateinit var cameraDevice: CameraDevice
    @Mock
    lateinit var device: Device

    @Test(expected = LevelOutOfRangeException::class)
    fun `Ensure zoom is not greater than 1f`() {
        // When
        device.updateZoomLevel(1.1f)

        // Then
        // Expect exception
    }

    @Test(expected = LevelOutOfRangeException::class)
    fun `Ensure zoom is not lower than 0f`() {
        // When
        device.updateZoomLevel(-0.1f)

        // Then
        // Expect exception
    }

    @Test
    fun `Update zoom`() = runBlocking {
        // Given
        device.awaitSelectedCamera() willReturn cameraDevice
        cameraDevice.getCapabilities() willReturn testCapabilities.copy(
                canZoom = true
        )

        // When
        device.updateZoomLevel(0.5f)

        // Then
        verify(cameraDevice).setZoom(0.5f)
    }

    @Test
    fun `Do not update zoom if it's not supported`() = runBlocking {
        // Given
        device.awaitSelectedCamera() willReturn cameraDevice
        cameraDevice.getCapabilities() willReturn testCapabilities.copy(
                canZoom = false
        )

        // When
        device.updateZoomLevel(0.5f)

        // Then
        verify(cameraDevice, never()).setZoom(anyFloat())
    }

}