package io.fotoapparat.hardware.v2.parameters;

import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.view.Surface;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import io.fotoapparat.parameter.Flash;
import io.fotoapparat.parameter.FocusMode;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@SuppressWarnings("NewApi")
@RunWith(MockitoJUnitRunner.class)
public class CaptureRequestBuilderTest {

    @Mock
    private CaptureRequest.Builder builder;
    @Mock
    private CameraDevice cameraDevice;
    @Mock
    private Surface surface;

    @Before
    public void setUp() throws Exception {
        given(cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE))
                .willReturn(builder);

        given(builder.build())
                .willReturn(Mockito.mock(CaptureRequest.class));
    }

    @Test
    public void onlyMandatoryParameters() throws Exception {
        // Given
        CaptureRequestBuilder builder = builderWithMandatoryArguments();

        // When
        CaptureRequest result = builder.build();

        // Then
        assertNotNull(result);
        assertEquals(Collections.singletonList(surface), builder.surfaces);
        assertEquals(cameraDevice, builder.cameraDevice);
        assertEquals(CameraDevice.TEMPLATE_STILL_CAPTURE, builder.requestTemplate);

    }

    @Test(expected = IllegalStateException.class)
    public void surfacesIsMandatory() throws Exception {
        // Given
        CaptureRequestBuilder builder = CaptureRequestBuilder
                .create(cameraDevice, CameraDevice.TEMPLATE_STILL_CAPTURE);

        // When
        builder.build();

        // Then
        // exception
    }

    @Test(expected = IllegalStateException.class)
    public void flashIsMandatoryWhenSettingExposure() throws Exception {
        // Given
        CaptureRequestBuilder builder = builderWithMandatoryArguments()
                .setExposureMode(true);

        // When
        builder.build();

        // Then
        // exception
    }

    @Test(expected = IllegalStateException.class)
    public void cannotBothTriggerAndCancelExposure() throws Exception {
        // Given
        CaptureRequestBuilder builder = builderWithMandatoryArguments()
                .triggerPrecaptureExposure(true)
                .cancelPrecaptureExposure(true);

        // When
        builder.build();

        // Then
        // exception
    }

    @Test
    public void surfacesListIsSet() throws Exception {
        // Given
        CaptureRequestBuilder builder = builderWithMandatoryArguments()
                .into(surface, surface);

        // When
        builder.build();

        // Then
        assertEquals(Arrays.asList(surface, surface), builder.surfaces);
    }

    @Test
    public void flashIsSet() throws Exception {
        // Given
        CaptureRequestBuilder builder = builderWithMandatoryArguments()
                .flash(Flash.AUTO_RED_EYE);

        // When
        builder.build();

        // Then
        assertEquals(Flash.AUTO_RED_EYE, builder.flash);
    }

    @Test
    public void focusIsSet() throws Exception {
        // Given
        CaptureRequestBuilder builder = builderWithMandatoryArguments()
                .focus(FocusMode.CONTINUOUS_FOCUS);

        // When
        builder.build();

        // Then
        assertEquals(FocusMode.CONTINUOUS_FOCUS, builder.focus);
    }

    @Test
    public void triggerAutoFocusIsSet() throws Exception {
        // Given
        CaptureRequestBuilder builder = builderWithMandatoryArguments()
                .triggerAutoFocus(true);

        // When
        builder.build();

        // Then
        assertTrue(builder.shouldTriggerAutoFocus);
    }

    private CaptureRequestBuilder builderWithMandatoryArguments() {
        return CaptureRequestBuilder
                .create(cameraDevice, CameraDevice.TEMPLATE_STILL_CAPTURE)
                .into(surface);
    }
}