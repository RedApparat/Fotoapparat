package io.fotoapparat.routine.camera

import io.fotoapparat.configuration.Configuration
import io.fotoapparat.hardware.CameraDevice
import io.fotoapparat.hardware.Device
import io.fotoapparat.test.testCameraParameters
import io.fotoapparat.test.testFrameProcessor
import io.fotoapparat.test.willReturn
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.inOrder
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class UpdateConfigurationRoutineTest {

    @Mock
    lateinit var device: Device
    @Mock
    lateinit var cameraDevice: CameraDevice

    @Test(expected = IllegalStateException::class)
    fun `Update device configuration, but camera has not started`() {
        // Given
        val configuration = Configuration()

        // When
        device.updateDeviceConfiguration(configuration)

        // Then
        // throw exception
    }

    @Test
    fun `Update device configuration`() {
        // Given
        val configuration = Configuration()
        device.getSelectedCamera() willReturn cameraDevice
        device.getCameraParameters(cameraDevice) willReturn testCameraParameters
        device.getFrameProcessor() willReturn testFrameProcessor

        // When
        device.updateDeviceConfiguration(configuration)

        // Then
        val inOrder = inOrder(
                device,
                cameraDevice
        )

        inOrder.verify(device).updateConfiguration(configuration)
        inOrder.verify(device).updateCameraConfiguration(cameraDevice)
    }

    @Test
    fun `Update camera configuration`() {
        // Given
        device.getCameraParameters(cameraDevice) willReturn testCameraParameters
        device.getFrameProcessor() willReturn testFrameProcessor

        // When
        device.updateCameraConfiguration(
                cameraDevice = cameraDevice
        )

        // Then
        val inOrder = inOrder(cameraDevice)

        inOrder.verify(cameraDevice).updateParameters(testCameraParameters)
        inOrder.verify(cameraDevice).updateFrameProcessor(testFrameProcessor)
    }
}