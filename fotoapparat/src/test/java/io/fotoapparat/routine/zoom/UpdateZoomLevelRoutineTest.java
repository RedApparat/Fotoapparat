package io.fotoapparat.routine.zoom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.range.Range;
import io.fotoapparat.parameter.range.Ranges;
import io.fotoapparat.routine.zoom.UpdateZoomLevelRoutine.LevelOutOfRangeException;

import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UpdateZoomLevelRoutineTest {

    @Mock
    CameraDevice cameraDevice;

    @InjectMocks
    UpdateZoomLevelRoutine testee;

    @SuppressWarnings("Range")
    @Test(expected = LevelOutOfRangeException.class)
    public void outOfRange_Higher() throws Exception {
        // When
        testee.updateZoomLevel(1.1f);

        // Then
        // Expect exception
    }

    @SuppressWarnings("Range")
    @Test(expected = LevelOutOfRangeException.class)
    public void outOfRange_Lower() throws Exception {
        // When
        testee.updateZoomLevel(-0.1f);

        // Then
        // Expect exception
    }

    @Test
    public void updateZoomLevel() throws Exception {
        // Given
        givenZoomSupported();

        // When
        testee.updateZoomLevel(0.5f);

        // Then
        verify(cameraDevice).setZoom(0.5f);
    }

    @Test
    public void zoomNotSupported() throws Exception {
        // Given
        givenZoomNotSupported();

        // When
        testee.updateZoomLevel(0.5f);

        // Then
        verify(cameraDevice, never()).setZoom(anyFloat());
    }

    private void givenZoomNotSupported() {
        givenZoom(false);
    }

    private void givenZoomSupported() {
        givenZoom(true);
    }

    private void givenZoom(boolean supported) {
        given(cameraDevice.getCapabilities())
                .willReturn(new Capabilities(
                        Collections.<Size>emptySet(),
                        Collections.<Size>emptySet(),
                        Collections.<FocusMode>emptySet(),
                        Collections.<Flash>emptySet(),
                        Collections.<Range<Integer>>emptySet(),
                        Ranges.<Integer>emptyRange(),
                        supported
                ));
    }

}