package io.fotoapparat.routine.parameter;

import org.junit.Before;
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
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.range.Ranges;
import io.fotoapparat.parameter.update.UpdateRequest;

import static io.fotoapparat.parameter.selector.FlashSelectors.torch;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.autoFocus;
import static io.fotoapparat.test.TestUtils.asSet;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UpdateParametersRoutineTest {

    @Mock
    CameraDevice cameraDevice;

    @InjectMocks
    UpdateParametersRoutine testee;

    @Before
    public void setUp() throws Exception {
        given(cameraDevice.getCapabilities())
                .willReturn(new Capabilities(
                        Collections.<Size>emptySet(),
                        Collections.<Size>emptySet(),
                        asSet(FocusMode.AUTO),
                        asSet(Flash.TORCH),
                        asSet(Ranges.continuousRange(30000, 30000)),
                        Ranges.continuousRange(1200),
                        false
                ));
    }

    @Test
    public void updateParameters() throws Exception {
        // Given
        UpdateRequest request = UpdateRequest.builder()
                .flash(torch())
                .focusMode(autoFocus())
                .build();

        // When
        testee.updateParameters(request);

        // Then
        verify(cameraDevice).updateParameters(
                new Parameters()
                        .putValue(
                                Parameters.Type.FLASH,
                                Flash.TORCH
                        )
                        .putValue(
                                Parameters.Type.FOCUS_MODE,
                                FocusMode.AUTO
                        )
        );
    }

}