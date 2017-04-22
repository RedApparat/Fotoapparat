package io.fotoapparat.hardware.v2.parameters;

import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.view.Surface;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SuppressWarnings("NewApi")
@RunWith(MockitoJUnitRunner.class)
public class RequestTest {

    @Mock
    private CaptureRequest.Builder builder;
    @Mock
    private CameraDevice cameraDevice;
    @Mock
    private CaptureRequest captureRequest;
    @Mock
    private Surface surface;

    @SuppressWarnings("WrongConstant")
    @Before
    public void setUp() throws Exception {
        given(cameraDevice.createCaptureRequest(anyInt()))
                .willReturn(builder);

        given(builder.build())
                .willReturn(captureRequest);
    }

    @Test
    public void stillCapture_setCaptureIntent() throws Exception {
        // Given
        CaptureRequestBuilder request = simpleRequest();

        // When
        Request.create(request);

        // Then
        verify(builder)
                .set(CaptureRequest.CONTROL_CAPTURE_INTENT,
                        CameraMetadata.CONTROL_CAPTURE_INTENT_STILL_CAPTURE);
        verify(builder)
                .set(CaptureRequest.CONTROL_MODE,
                        CaptureRequest.CONTROL_MODE_AUTO);
    }

    @Test
    public void stillCapture_notSetCaptureIntent() throws Exception {
        // Given
        CaptureRequestBuilder request = CaptureRequestBuilder
                .create(
                        cameraDevice,
                        CameraDevice.TEMPLATE_PREVIEW
                )
                .into(surface);

        // When
        Request.create(request);

        // Then
        verify(builder, never())
                .set(CaptureRequest.CONTROL_CAPTURE_INTENT,
                        CameraMetadata.CONTROL_CAPTURE_INTENT_STILL_CAPTURE);
        verify(builder, never())
                .set(CaptureRequest.CONTROL_MODE,
                        CaptureRequest.CONTROL_MODE_AUTO);
    }

    private CaptureRequestBuilder simpleRequest() {
        return CaptureRequestBuilder
                .create(
                        cameraDevice,
                        CameraDevice.TEMPLATE_STILL_CAPTURE
                )
                .into(surface);
    }
}