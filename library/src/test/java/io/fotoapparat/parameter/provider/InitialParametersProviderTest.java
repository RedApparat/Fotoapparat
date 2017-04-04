package io.fotoapparat.parameter.provider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.parameter.selector.SelectorFunction;

import static io.fotoapparat.test.TestUtils.asSet;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class InitialParametersProviderTest {

    static final Set<FocusMode> FOCUS_MODES = asSet(FocusMode.FIXED);

    @Mock
    CameraDevice cameraDevice;
    @Mock
    SelectorFunction<FocusMode> focusModeSelector;

    @InjectMocks
    InitialParametersProvider testee;

    @Before
    public void setUp() throws Exception {
        given(cameraDevice.getCapabilities())
                .willReturn(new Capabilities(
                        FOCUS_MODES
                ));
    }

    @Test
    public void selectFocusMode() throws Exception {
        // Given
        given(focusModeSelector.select(FOCUS_MODES))
                .willReturn(FocusMode.FIXED);

        // When
        Parameters parameters = testee.initialParameters();

        // Then
        assertEquals(
                FocusMode.FIXED,
                parameters.getValue(Parameters.Type.FOCUS_MODE)
        );
    }

}