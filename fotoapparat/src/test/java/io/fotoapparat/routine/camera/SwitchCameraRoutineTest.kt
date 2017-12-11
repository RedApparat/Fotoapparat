package io.fotoapparat.routine.camera

import io.fotoapparat.characteristic.LensPosition
import io.fotoapparat.hardware.CameraDevice
import io.fotoapparat.hardware.Device
import io.fotoapparat.test.testConfiguration
import io.fotoapparat.test.willReturn
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class SwitchCameraRoutineTest {

    @Mock
    lateinit var oldCameraDevice: CameraDevice
    @Mock
    lateinit var device: Device

    @Test
    fun `Switch camera, not started`() {
        // Given
        val lensPositionSelector: Collection<LensPosition>.() -> LensPosition.Front = { LensPosition.Front }

        // When
        device.switchCamera(
                newLensPositionSelector = lensPositionSelector,
                newConfiguration = testConfiguration,
                mainThreadErrorCallback = {}
        )

        // Then
        verify(device).updateLensPositionSelector(lensPositionSelector)
        verify(device).updateConfiguration(testConfiguration)
    }

    @Test
    fun `Switch camera, same lens position`() {
        // Given
        val lensPositionSelector: Collection<LensPosition>.() -> LensPosition.Front = { LensPosition.Front }
        device.getLensPositionSelector() willReturn lensPositionSelector
        device.getSelectedCamera() willReturn oldCameraDevice

        // When
        device.switchCamera(
                newLensPositionSelector = lensPositionSelector,
                newConfiguration = testConfiguration,
                mainThreadErrorCallback = {}
        )

        // Then
        val inOrder = inOrder(
                device,
                oldCameraDevice
        )
        inOrder.apply {
            verify(device, never()).updateLensPositionSelector(lensPositionSelector)
            verify(device, never()).updateConfiguration(testConfiguration)
        }
    }

    @Test
    fun `Switch camera, different lens position`() {
        // Given
        val lensPositionSelector: Collection<LensPosition>.() -> LensPosition.Front = { LensPosition.Front }
        device.getLensPositionSelector() willReturn { LensPosition.Back }
        device.getSelectedCamera() willReturn oldCameraDevice

        // When
        device.switchCamera(
                newLensPositionSelector = lensPositionSelector,
                newConfiguration = testConfiguration,
                mainThreadErrorCallback = {}
        )

        // Then
        val inOrder = inOrder(
                device,
                oldCameraDevice
        )
        inOrder.apply {
            verify(device).updateLensPositionSelector(lensPositionSelector)
            verify(device).updateConfiguration(testConfiguration)
        }
    }

}