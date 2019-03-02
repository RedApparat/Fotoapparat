package io.fotoapparat.hardware.orientation

import io.fotoapparat.hardware.Device
import io.fotoapparat.hardware.orientation.Orientation.Horizontal.Landscape
import io.fotoapparat.hardware.orientation.Orientation.Vertical.Portrait
import io.fotoapparat.hardware.orientation.Orientation.Vertical.ReversePortrait
import io.fotoapparat.test.willReturn
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.atomic.AtomicReference
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
internal class OrientationSensorTest {

    @Mock
    lateinit var rotationListener: RotationListener
    @Mock
    lateinit var device: Device

    lateinit var testee: OrientationSensor

    @Before
    fun setUp() {
        device.getScreenOrientation() willReturn Portrait
        testee = OrientationSensor(
                rotationListener,
                device
        )
    }

    @Test
    fun lifecycle() {
        // Given

        // When
        testee.start {}
        testee.stop()

        // Then
        verify(rotationListener).enable()
        verify(rotationListener).disable()
    }

    @Test
    fun singleEvent() {
        // Given
        device.getScreenOrientation() willReturn Landscape

        val atomicReference = AtomicReference<OrientationState>()

        testee.start { orientationState: OrientationState ->
            atomicReference.set(orientationState)
        }

        // When
        rotationListener.orientationChanged(ReversePortrait.degrees)

        // Then
        assertEquals(
                expected = OrientationState(
                        deviceOrientation = ReversePortrait,
                        screenOrientation = Landscape
                ),
                actual = atomicReference.get()
        )
    }
}