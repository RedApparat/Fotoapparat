package io.fotoapparat.routine.parameter

import io.fotoapparat.hardware.CameraDevice
import io.fotoapparat.hardware.Device
import io.fotoapparat.test.testCameraParameters
import io.fotoapparat.test.willReturn
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
internal class GetParametersRoutineTest {

    @Mock
    lateinit var cameraDevice: CameraDevice
    @Mock
    lateinit var device: Device

    @Test
    fun `Get camera parameters`() = runBlocking {
        // Given
        device.awaitSelectedCamera() willReturn cameraDevice
        cameraDevice.getParameters() willReturn testCameraParameters

        // When
        val currentParameters = device.getCurrentParameters()

        // Then
        assertEquals(
                expected = testCameraParameters,
                actual = currentParameters
        )
    }
}