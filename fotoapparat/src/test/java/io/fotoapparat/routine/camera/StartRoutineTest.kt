package io.fotoapparat.routine.camera

import android.graphics.SurfaceTexture
import io.fotoapparat.exception.camera.CameraException
import io.fotoapparat.hardware.CameraDevice
import io.fotoapparat.hardware.Device
import io.fotoapparat.hardware.orientation.OrientationSensor
import io.fotoapparat.log.Logger
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.test.testResolution
import io.fotoapparat.test.willReturn
import io.fotoapparat.test.willThrow
import io.fotoapparat.view.CameraRenderer
import io.fotoapparat.view.Preview
import io.fotoapparat.view.toPreview
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
internal class StartRoutineTest {

    @Mock
    lateinit var surfaceTexture: SurfaceTexture
    @Mock
    lateinit var cameraViewRenderer: CameraRenderer
    @Mock
    lateinit var orientationSensor: OrientationSensor
    @Mock
    lateinit var cameraDevice: CameraDevice
    @Mock
    lateinit var mockLogger: Logger
    @Mock
    lateinit var device: Device

    lateinit var preview: Preview

    @Before
    fun setUp() {
        preview = surfaceTexture.toPreview()
    }

    @Test
    fun `Start camera`() {
        // Given
        device.apply {
            getSelectedCamera() willReturn cameraDevice
            cameraRenderer willReturn cameraViewRenderer
            scaleType willReturn ScaleType.CenterCrop
        }
        cameraDevice.getPreviewResolution() willReturn testResolution
        cameraViewRenderer.getPreview() willReturn preview

        // When
        device.start(orientationSensor)

        // Then
        val inOrder = inOrder(
                device,
                cameraDevice,
                cameraViewRenderer
        )

        inOrder.apply {
            verify(device).selectCamera()
            verify(cameraDevice).open()
            verify(device).updateCameraConfiguration(cameraDevice)
            verify(cameraDevice).setDisplayOrientation(orientationSensor.lastKnownOrientationState)
            verify(cameraViewRenderer).setScaleType(ScaleType.CenterCrop)
            verify(cameraViewRenderer).setPreviewResolution(testResolution)
            verify(cameraDevice).setDisplaySurface(preview)
            verify(cameraDevice).startPreview()
        }
    }

    @Test
    fun `Cannot set display surface while starting`() {
        // Given
        device.apply {
            getSelectedCamera() willReturn cameraDevice
            cameraRenderer willReturn cameraViewRenderer
            scaleType willReturn ScaleType.CenterCrop
            logger willReturn mockLogger
        }
        cameraDevice.getPreviewResolution() willReturn testResolution
        cameraDevice.setDisplaySurface(preview) willThrow IOException()
        cameraViewRenderer.getPreview() willReturn preview

        // When
        device.start(orientationSensor)

        // Then
        verify(cameraDevice, never()).startPreview()
    }

    @Test(expected = IllegalStateException::class)
    fun `Boot start, but camera has already started`() {
        // Given
        device.hasSelectedCamera() willReturn true

        // When
        device.bootStart(
                orientationSensor = orientationSensor,
                mainThreadErrorCallback = {}
        )

        // Then
        // throw exception
    }

    @Test
    fun `Boot start, but camera cannot be selected`() {
        // Given
        device.getSelectedCamera() willThrow CameraException("Camera cannot be selected")
        val hasErrors = AtomicBoolean(false)

        // When
        device.bootStart(
                orientationSensor = orientationSensor,
                mainThreadErrorCallback = { hasErrors.set(true) }
        )

        // Then
        assertTrue(hasErrors.get())
    }

    @Test
    fun `Boot start`() {
        // Given
        var hasErrors = false

        device.apply {
            hasSelectedCamera() willReturn false
            getSelectedCamera() willReturn cameraDevice
            cameraRenderer willReturn cameraViewRenderer
            scaleType willReturn ScaleType.CenterCrop
        }
        cameraDevice.getPreviewResolution() willReturn testResolution
        cameraViewRenderer.getPreview() willReturn surfaceTexture.toPreview()

        // When
        device.bootStart(
                orientationSensor = orientationSensor,
                mainThreadErrorCallback = { hasErrors = true }
        )

        // Then
        assertFalse(hasErrors)

        verify(device).start(orientationSensor)
    }

}
