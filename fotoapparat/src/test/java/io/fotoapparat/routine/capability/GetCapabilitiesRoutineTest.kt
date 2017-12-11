package io.fotoapparat.routine.capability

import io.fotoapparat.hardware.CameraDevice
import io.fotoapparat.hardware.Device
import io.fotoapparat.test.testCapabilities
import io.fotoapparat.test.willReturn
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
internal class GetCapabilitiesRoutineTest {

    @Mock
    lateinit var cameraDevice: CameraDevice
    @Mock
    lateinit var device: Device

    @Test(expected = IllegalStateException::class)
    fun `Get capabilities, but camera has not started`() {
        // When
        device.getCapabilities()

        // Then
        // throw exception
    }

    @Test
    fun `Get capabilities`() {
        // Given
        device.getSelectedCamera() willReturn cameraDevice
        cameraDevice.getCapabilities() willReturn testCapabilities

        // When
        val capabilities = device.getCapabilities()

        // Then
        assertEquals(
                expected = testCapabilities,
                actual = capabilities
        )
    }

}