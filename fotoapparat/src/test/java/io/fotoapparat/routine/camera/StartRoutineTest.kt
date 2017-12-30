package io.fotoapparat.routine.camera

import android.graphics.SurfaceTexture
import io.fotoapparat.exception.camera.CameraException
import io.fotoapparat.hardware.CameraDevice
import io.fotoapparat.hardware.Device
import io.fotoapparat.hardware.orientation.OrientationSensor
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.test.testResolution
import io.fotoapparat.test.willReturn
import io.fotoapparat.test.willThrow
import io.fotoapparat.view.CameraRenderer
import io.fotoapparat.view.Preview
import io.fotoapparat.view.toPreview
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
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
            getScreenRotation() willReturn 90
            cameraRenderer willReturn cameraViewRenderer
            scaleType willReturn ScaleType.CenterCrop
        }
        cameraDevice.getPreviewResolution() willReturn testResolution
        cameraViewRenderer.getPreview() willReturn preview

        // When
        device.start()

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
            verify(cameraDevice).setDisplayOrientation(90)
            verify(cameraViewRenderer).setScaleType(ScaleType.CenterCrop)
            verify(cameraViewRenderer).setPreviewResolution(testResolution)
            verify(cameraDevice).setDisplaySurface(preview)
            verify(cameraDevice).startPreview()
        }
    }

    @Test(expected = IllegalStateException::class)
    fun `Boot start, but camera has already started`() {
        // Given
        device.hasSelectedCamera() willReturn true

        // When
        device.bootStart(
                orientationSensor,
                {}
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
                orientationSensor,
                { hasErrors.set(true) }
        )

        // Then
        assertTrue(hasErrors.get())
    }

    @Test
    fun `Boot start`() = runBlocking {
        // Given
        val hasErrors = AtomicBoolean(false)
        device.apply {
            hasSelectedCamera() willReturn false
            getSelectedCamera() willReturn cameraDevice
            getScreenRotation() willReturn 90
            cameraRenderer willReturn cameraViewRenderer
            scaleType willReturn ScaleType.CenterCrop
        }
        cameraDevice.getPreviewResolution() willReturn testResolution
        cameraViewRenderer.getPreview() willReturn surfaceTexture.toPreview()

        // When
        device.bootStart(
                orientationSensor,
                { hasErrors.set(true) }
        )

        // Then
        assertFalse(hasErrors.get())

        verify(device).start()
    }

}
