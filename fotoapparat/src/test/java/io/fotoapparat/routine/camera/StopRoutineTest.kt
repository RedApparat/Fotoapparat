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
        device.stop(cameraDevice)

        // Then
        val inOrder = inOrder(
                device,
                cameraDevice
        )

        inOrder.apply {
            verify(cameraDevice).stopPreview()
            verify(cameraDevice).close()
            verify(device).clearSelectedCamera()
        }

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
            verify(orientationSensor).stopMonitoring()
            verify(device).stop(cameraDevice)
        }
    }

}