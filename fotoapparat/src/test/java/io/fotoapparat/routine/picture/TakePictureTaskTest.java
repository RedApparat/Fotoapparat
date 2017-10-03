package io.fotoapparat.routine.picture;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.lens.FocusResult;
import io.fotoapparat.parameter.FocusMode;
import io.fotoapparat.parameter.Parameters;
import io.fotoapparat.photo.Photo;

import static io.fotoapparat.test.TestUtils.resultOf;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TakePictureTaskTest {

    static final Photo PHOTO = Photo.empty();

    static final Parameters PARAMETERS_WITH_AUTO_FOCUS = new Parameters()
            .putValue(Parameters.Type.FOCUS_MODE, FocusMode.AUTO);

    static final Parameters PARAMETERS_WITH_CONTINUOUS_FOCUS = new Parameters()
            .putValue(Parameters.Type.FOCUS_MODE, FocusMode.CONTINUOUS_FOCUS);

    @Mock
    CameraDevice cameraDevice;

    @InjectMocks
    TakePictureTask testee;

    @Before
    public void setUp() throws Exception {
        given(cameraDevice.takePicture())
                .willReturn(PHOTO);

        given(cameraDevice.getCurrentParameters())
                .willReturn(PARAMETERS_WITH_AUTO_FOCUS);
    }

    @Test
    public void noFocusAfter3Attempts_takePicture() throws Exception {
        // Given
        given(cameraDevice.autoFocus())
                .willReturn(new FocusResult(false, true));

        // When
        Photo result = resultOf(testee);

        // Then
        InOrder inOrder = inOrder(cameraDevice);
        inOrder.verify(cameraDevice, times(3)).autoFocus();
        inOrder.verify(cameraDevice).takePicture();
        inOrder.verify(cameraDevice).startPreview();

        assertEquals(result, PHOTO);
    }

    @Test
    public void exposureMeasurementRequired_takePhoto() throws Exception {
        // Given
        given(cameraDevice.autoFocus())
                .willReturn(new FocusResult(true, true));

        // When
        Photo result = resultOf(testee);

        // Then
        InOrder inOrder = inOrder(cameraDevice);
        inOrder.verify(cameraDevice).autoFocus();
        inOrder.verify(cameraDevice).measureExposure();
        inOrder.verify(cameraDevice).takePicture();
        inOrder.verify(cameraDevice).startPreview();

        assertEquals(result, PHOTO);
    }

    @Test
    public void takePhoto() throws Exception {
        // Given
        given(cameraDevice.autoFocus())
                .willReturn(new FocusResult(true, false));

        // When
        Photo result = resultOf(testee);

        // Then
        InOrder inOrder = inOrder(cameraDevice);
        inOrder.verify(cameraDevice).autoFocus();
        inOrder.verify(cameraDevice).takePicture();
        inOrder.verify(cameraDevice).startPreview();

        assertEquals(result, PHOTO);
    }

    @Test
    public void doNotFocusInContinuousFocusMode() throws Exception {
        // Given
        given(cameraDevice.getCurrentParameters())
                .willReturn(PARAMETERS_WITH_CONTINUOUS_FOCUS);

        // When
        Photo result = resultOf(testee);

        // Then
        verify(cameraDevice, never()).autoFocus();

        assertEquals(result, PHOTO);
    }

    @Test
    public void startPreviewFailed() throws Exception {
        // Given
        given(cameraDevice.autoFocus())
                .willReturn(new FocusResult(true, false));

        doThrow(new CameraException("test"))
                .when(cameraDevice)
                .startPreview();

        // When
        Photo result = resultOf(testee);

        // Then
        verify(cameraDevice).startPreview();

        assertEquals(result, PHOTO);
    }

}