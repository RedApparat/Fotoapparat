package io.fotoapparat.routine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collection;
import java.util.List;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.parameter.LensPosition;
import io.fotoapparat.parameter.selector.SelectorFunction;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CheckAvailabilityRoutineTest {

    static final List<LensPosition> LENS_POSITIONS = asList(
            LensPosition.FRONT,
            LensPosition.BACK
    );

    @Mock
    CameraDevice cameraDevice;
    @Mock
    SelectorFunction<Collection<LensPosition>, LensPosition> lensPositionSelector;

    @InjectMocks
    CheckAvailabilityRoutine testee;

    @Test
    public void available() throws Exception {
        // Given
        givenLensPositionsAreReturned();

        given(lensPositionSelector.select(LENS_POSITIONS))
                .willReturn(LensPosition.FRONT);

        // When
        boolean result = testee.isAvailable();

        // Then
        assertTrue(result);
    }

    @Test
    public void notAvailable() throws Exception {
        // Given
        givenLensPositionsAreReturned();

        given(lensPositionSelector.select(LENS_POSITIONS))
                .willReturn(null);

        // When
        boolean result = testee.isAvailable();

        // Then
        assertFalse(result);
    }

    private void givenLensPositionsAreReturned() {
        given(cameraDevice.getAvailableLensPositions())
                .willReturn(LENS_POSITIONS);
    }
}