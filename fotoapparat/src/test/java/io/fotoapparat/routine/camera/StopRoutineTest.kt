package io.fotoapparat.routine.camera

import io.fotoapparat.hardware.CameraDevice
import io.fotoapparat.hardware.Device
import io.fotoapparat.hardware.orientation.OrientationSensor
import io.fotoapparat.routine.orientation.stopMonitoring
import io.fotoapparat.test.willReturn
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.inOrder
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class StopRoutineTest {

    @Mock
    lateinit var orientationSensor: OrientationSensor
    @Mock
    lateinit var cameraDevice: CameraDevice
    @Mock
    lateinit var device: Device

    @Test
    fun `Stop camera`() {
        // When
        cameraDevice.stop()

        // Then
        val inOrder = inOrder(cameraDevice)
        inOrder.verify(cameraDevice).stopPreview()
        inOrder.verify(cameraDevice).close()
    }

    @Test(expected = IllegalStateException::class)
    fun `Shut down camera, but camera has not started`() {
        // When
        device.shutDown(
                orientationSensor = orientationSensor
        )

        // Then
        // throw exception
    }

    @Test
    fun `Shut down camera`() {
        // Given
        device.getSelectedCamera() willReturn cameraDevice

        // When
        device.shutDown(
                orientationSensor = orientationSensor
        )

        // Then
        val inOrder = inOrder(
                device,
                cameraDevice,
                orientationSensor
        )

        inOrder.apply {
            verify(cameraDevice).stop()
            verify(orientationSensor).stopMonitoring()
            verify(device).clearSelectedCamera()
        }
    }

}