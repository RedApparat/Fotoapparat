package io.fotoapparat.routine.camera

import io.fotoapparat.characteristic.LensPosition
import io.fotoapparat.error.CameraErrorCallback
import io.fotoapparat.hardware.CameraDevice
import io.fotoapparat.hardware.Device
import io.fotoapparat.hardware.orientation.OrientationSensor
import io.fotoapparat.test.testConfiguration
import io.fotoapparat.test.willReturn
import io.fotoapparat.view.CameraRenderer
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class SwitchCameraRoutineTest {

    @Mock
    lateinit var oldCameraDevice: CameraDevice
    @Mock
    lateinit var newCameraDevice: CameraDevice
    @Mock
    lateinit var device: Device
    @Mock
    lateinit var cameraRenderer: CameraRenderer
    @Mock
    lateinit var orientationSensor: OrientationSensor

    private val mainThreadErrorCallback: CameraErrorCallback = {}

    @Test
    fun `Switch camera, not started`() {
        // Given
        given(device.getSelectedCamera())
                .willThrow(IllegalStateException())
                .willReturn(oldCameraDevice)
        device.cameraRenderer willReturn cameraRenderer
        val lensPositionSelector: Iterable<LensPosition>.() -> LensPosition.Front = { LensPosition.Front }

        // When
        device.switchCamera(
                newLensPositionSelector = lensPositionSelector,
                newConfiguration = testConfiguration,
                mainThreadErrorCallback = mainThreadErrorCallback,
                orientationSensor = orientationSensor
        )

        // Then
        verify(device).updateLensPositionSelector(lensPositionSelector)
        verify(device).updateConfiguration(testConfiguration)
        verify(device, never()).restartPreview(oldCameraDevice, orientationSensor, mainThreadErrorCallback)
    }

    @Test
    fun `Switch camera, same lens position`() {
        // Given
        val lensPositionSelector: Iterable<LensPosition>.() -> LensPosition.Front = { LensPosition.Front }
        device.getLensPositionSelector() willReturn lensPositionSelector
        device.getSelectedCamera() willReturn oldCameraDevice
        device.cameraRenderer willReturn cameraRenderer

        // When
        device.switchCamera(
                newLensPositionSelector = lensPositionSelector,
                newConfiguration = testConfiguration,
                mainThreadErrorCallback = mainThreadErrorCallback,
                orientationSensor = orientationSensor
        )

        // Then
        val inOrder = inOrder(
                device,
                oldCameraDevice
        )
        inOrder.apply {
            verify(device, never()).updateLensPositionSelector(lensPositionSelector)
            verify(device, never()).updateConfiguration(testConfiguration)
            verify(device, never()).restartPreview(oldCameraDevice, orientationSensor, mainThreadErrorCallback)
        }
    }

    @Test
    fun `Switch camera, different lens position`() {
        // Given
        val lensPositionSelector: Iterable<LensPosition>.() -> LensPosition.Front = { LensPosition.Front }
        device.getLensPositionSelector() willReturn { LensPosition.Back }
        device.getSelectedCamera() willReturn oldCameraDevice
        device.cameraRenderer willReturn cameraRenderer

        // When
        device.switchCamera(
                newLensPositionSelector = lensPositionSelector,
                newConfiguration = testConfiguration,
                mainThreadErrorCallback = mainThreadErrorCallback,
                orientationSensor = orientationSensor
        )

        // Then
        val inOrder = inOrder(
                device,
                oldCameraDevice
        )
        inOrder.apply {
            verify(device).updateLensPositionSelector(lensPositionSelector)
            verify(device).updateConfiguration(testConfiguration)
            verify(device).restartPreview(oldCameraDevice, orientationSensor, mainThreadErrorCallback)
        }
    }

    @Test
    fun `Restart preview`() {
        // Given
        device.getSelectedCamera() willReturn newCameraDevice
        device.cameraRenderer willReturn cameraRenderer

        // When
        device.restartPreview(
                oldCameraDevice = oldCameraDevice,
                orientationSensor = orientationSensor,
                mainThreadErrorCallback = mainThreadErrorCallback
        )

        // Then
        val inOrder = inOrder(device)

        inOrder.apply {
            verify(device).stop(oldCameraDevice)
            verify(device).start(orientationSensor)
        }
    }

    @Test
    fun `Restart preview, but camera couldn't open`() {
        // Given
        device.getSelectedCamera() willReturn newCameraDevice
        device.cameraRenderer willReturn cameraRenderer

        // When
        device.restartPreview(
                oldCameraDevice = oldCameraDevice,
                orientationSensor = orientationSensor,
                mainThreadErrorCallback = mainThreadErrorCallback
        )

        // Then
        val inOrder = inOrder(device)
        inOrder.apply {
            verify(device).stop(oldCameraDevice)
            verify(device).start(orientationSensor)
        }
    }

}