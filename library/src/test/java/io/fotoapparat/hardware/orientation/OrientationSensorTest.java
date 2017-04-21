package io.fotoapparat.hardware.orientation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrientationSensorTest {

    @Mock
    RotationListener rotationListener;
    @Mock
    ScreenOrientationProvider screenOrientationProvider;
    @InjectMocks
    OrientationSensor testee;

    @Test
    public void setListenerCalled() throws Exception {
        // Then
        verify(rotationListener).setRotationListener(testee);
    }

    @Test
    public void lifecycle() throws Exception {
        // Given
        OrientationSensor.Listener mockListener = Mockito.mock(OrientationSensor.Listener.class);

        // When
        testee.start(mockListener);
        testee.stop();

        // Then
        verify(rotationListener).enable();
        verify(rotationListener).disable();
    }

    @Test
    public void singleEvent() throws Exception {
        // Given
        given(screenOrientationProvider.getScreenRotation())
                .willReturn(90);
        final AtomicInteger atomicInteger = new AtomicInteger();
        testee.start(new OrientationSensor.Listener() {
            @Override
            public void onOrientationChanged(int degrees) {
                atomicInteger.set(degrees);
            }
        });

        // When
        testee.onRotationChanged();

        // Then
        assertEquals(90, atomicInteger.get());
    }
}