package io.fotoapparat.hardware.v1;

import android.hardware.Camera;
import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import io.fotoapparat.hardware.Capabilities;
import io.fotoapparat.hardware.v1.capabilities.CapabilitiesFactory;
import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.parameter.range.Ranges;

import static io.fotoapparat.test.TestUtils.asSet;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("deprecation")
public class CapabilitiesFactoryTest {

    @Mock
    Camera camera;
    @Mock
    CameraParametersDecorator parametersProvider;

    CapabilitiesFactory testee;

    @Before
    public void setUp() throws Exception {
        given(parametersProvider.getSupportedFocusModes())
                .willReturn(Collections.<String>emptyList());
        given(parametersProvider.getSupportedFlashModes())
                .willReturn(Collections.<String>emptyList());
        given(parametersProvider.getSupportedPictureSizes())
                .willReturn(Collections.<Camera.Size>emptyList());
        given(parametersProvider.getSupportedPreviewSizes())
                .willReturn(Collections.<Camera.Size>emptyList());
        given(parametersProvider.getSupportedPreviewFpsRange())
                .willReturn(Collections.<int[]>emptyList());
        given(parametersProvider.isZoomSupported())
                .willReturn(false);
        given(parametersProvider.getSensorSensitivityValues())
            .willReturn(Collections.<Integer>emptySet());

        testee = new CapabilitiesFactory();
    }

    @Test
    public void mapFocusModes() throws Exception {
        // Given
        given(parametersProvider.getSupportedFocusModes())
                .willReturn(asList(
                        Camera.Parameters.FOCUS_MODE_AUTO,
                        Camera.Parameters.FOCUS_MODE_FIXED,
                        Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE,
                        Camera.Parameters.FOCUS_MODE_INFINITY,
                        Camera.Parameters.FOCUS_MODE_MACRO,
                        "something random"
                ));

        // When
        Capabilities capabilities = testee.fromParameters(parametersProvider);

        // Then
        assertEquals(
                asSet(
                        FocusMode.AUTO,
                        FocusMode.FIXED,
                        FocusMode.CONTINUOUS_FOCUS,
                        FocusMode.INFINITY,
                        FocusMode.MACRO
                ),
                capabilities.supportedFocusModes()
        );
    }

    @Test
    public void mapFocusModes_EmptyList_AlwaysIncludeFixed() throws Exception {
        // Given
        given(parametersProvider.getSupportedFocusModes())
                .willReturn(Collections.<String>emptyList());

        // When
        Capabilities capabilities = testee.fromParameters(parametersProvider);

        // Then
        assertEquals(
                singleton(FocusMode.FIXED),
                capabilities.supportedFocusModes()
        );
    }

    @Test
    public void mapFlashModes() throws Exception {
        // Given
        given(parametersProvider.getSupportedFlashModes())
                .willReturn(asList(
                        Camera.Parameters.FLASH_MODE_AUTO,
                        Camera.Parameters.FLASH_MODE_ON,
                        Camera.Parameters.FLASH_MODE_RED_EYE,
                        Camera.Parameters.FLASH_MODE_TORCH,
                        Camera.Parameters.FLASH_MODE_OFF
                ));

        // When
        Capabilities capabilities = testee.fromParameters(parametersProvider);

        // Then
        assertEquals(
                asSet(
                        Flash.AUTO,
                        Flash.ON,
                        Flash.AUTO_RED_EYE,
                        Flash.TORCH,
                        Flash.OFF
                ),
                capabilities.supportedFlashModes()
        );
    }

    @Test
    public void mapFlashModes_Null() throws Exception {
        // Given
        given(parametersProvider.getSupportedFlashModes())
                .willReturn(null);    // because why the fuck not, right Google?

        // When
        Capabilities capabilities = testee.fromParameters(parametersProvider);

        // Then
        assertEquals(
                singleton(Flash.OFF),
                capabilities.supportedFlashModes()
        );
    }

    @Test
    public void mapPictureSizes() throws Exception {
        // Given
        given(parametersProvider.getSupportedPictureSizes())
                .willReturn(asList(
                        makeSize(10, 10),
                        makeSize(20, 20)
                ));

        // When
        Capabilities capabilities = testee.fromParameters(parametersProvider);

        // Then
        assertEquals(
                asSet(
                        new Size(10, 10),
                        new Size(20, 20)
                ),
                capabilities.supportedPictureSizes()
        );
    }

    @Test
    public void mapPreviewSizes() throws Exception {
        // Given
        given(parametersProvider.getSupportedPreviewSizes())
                .willReturn(asList(
                        makeSize(10, 10),
                        makeSize(20, 20)
                ));

        // When
        Capabilities capabilities = testee.fromParameters(parametersProvider);

        // Then
        assertEquals(
                asSet(
                        new Size(10, 10),
                        new Size(20, 20)
                ),
                capabilities.supportedPreviewSizes()
        );
    }

    @Test
    public void mapPreviewFpsRanges() throws Exception {
        // Given
        given(parametersProvider.getSupportedPreviewFpsRange())
                .willReturn(asList(
                        new int[] {24000, 24000},
                        new int[] {30000, 30000}
                ));

        // When
        Capabilities capabilities = testee.fromParameters(parametersProvider);

        // Then
        assertEquals(
                asSet(
                        Ranges.continuousRange(24000, 24000),
                        Ranges.continuousRange(30000, 30000)
                ),
                capabilities.supportedPreviewFpsRanges()
        );
    }

    @Test
    public void mapSensorSensitivityRange() throws Exception {
        // Given
        given(parametersProvider.getSensorSensitivityValues())
                .willReturn(asSet(200, 400));

        // When
        Capabilities capabilities = testee.fromParameters(parametersProvider);

        // Then
        assertEquals(
                Ranges.discreteRange(asList(200, 400)),
                capabilities.supportedSensorSensitivityRange()
        );
    }

    @Test
    public void zoomSupported() throws Exception {
        // Given
        given(parametersProvider.isZoomSupported())
                .willReturn(true);

        // When
        Capabilities capabilities = testee.fromParameters(parametersProvider);

        assertTrue(capabilities.isZoomSupported());
    }

    @NonNull
    private Camera.Size makeSize(int width, int height) {
        Camera.Size size = camera.new Size(0, 0);
        size.width = width;
        size.height = height;

        return size;
    }
}