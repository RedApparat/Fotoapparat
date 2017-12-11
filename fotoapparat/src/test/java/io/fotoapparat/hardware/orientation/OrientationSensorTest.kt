package io.fotoapparat.hardware.orientation

import io.fotoapparat.hardware.Device
import io.fotoapparat.test.willReturn
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.atomic.AtomicInteger
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
        testee = OrientationSensor(
                rotationListener,
                device
        )
    }

    @Test
    fun lifecycle() {
        // Given

        // When
        testee.start({})
        testee.stop()

        // Then
        verify(rotationListener).enable()
        verify(rotationListener).disable()
    }

    @Test
    fun singleEvent() {
        // Given
        device.getScreenRotation() willReturn 90

        val atomicInteger = AtomicInteger()

        testee.start { degrees ->
            atomicInteger.set(degrees)
        }

        // When
        rotationListener.orientationChanged()

        // Then
        assertEquals(
                expected = 90,
                actual = atomicInteger.get()
        )
    }
}