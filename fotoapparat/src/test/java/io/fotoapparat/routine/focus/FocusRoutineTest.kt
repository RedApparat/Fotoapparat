package io.fotoapparat.routine.focus

import io.fotoapparat.hardware.CameraDevice
import io.fotoapparat.hardware.Device
import io.fotoapparat.hardware.metering.FocalRequest
import io.fotoapparat.hardware.metering.PointF
import io.fotoapparat.parameter.Resolution
import io.fotoapparat.result.FocusResult
import io.fotoapparat.test.willReturn
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.inOrder
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
internal class FocusRoutineTest {

    @Mock
    lateinit var cameraDevice: CameraDevice
    @Mock
    lateinit var device: Device

    @Test
    fun Focus() = runBlocking {
        // Given
        device.awaitSelectedCamera() willReturn cameraDevice
        cameraDevice.autoFocus() willReturn FocusResult.Focused

        // When
        val focusResult = device.focus()

        // Then
        assertEquals(
                expected = FocusResult.Focused,
                actual = focusResult
        )
    }


    @Test
    fun `Focus on point`() = runBlocking {
        // Given
        val focalRequest = FocalRequest(
                point = PointF(
                        x = 100f,
                        y = 100f
                ),
                previewResolution = Resolution(
                        width = 1000,
                        height = 1000
                )
        )
        device.awaitSelectedCamera() willReturn cameraDevice
        cameraDevice.autoFocus() willReturn FocusResult.Focused

        // When
        val focusResult = device.focusOnPoint(focalRequest)

        // Then
        inOrder(cameraDevice).verify(cameraDevice).apply {
            setFocalPoint(focalRequest)
            autoFocus()
        }

        assertEquals(
                expected = FocusResult.Focused,
                actual = focusResult
        )
    }
}