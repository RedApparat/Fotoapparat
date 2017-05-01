package io.fotoapparat.routine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.lens.FocusResultState;
import io.fotoapparat.photo.Photo;

import static io.fotoapparat.test.TestUtils.resultOf;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class TakePictureTaskTest {

    static final Photo PHOTO = Photo.empty();

    @Mock
    CameraDevice cameraDevice;

    @InjectMocks
    TakePictureTask testee;

    @Before
    public void setUp() throws Exception {
        given(cameraDevice.takePicture())
                .willReturn(PHOTO);
    }

    @Test
    public void noFocusAfter3Attempts_takePicture() throws Exception {
        // Given
        given(cameraDevice.autoFocus())
                .willReturn(new FocusResultState(false, true));

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
                .willReturn(new FocusResultState(true, true));

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
                .willReturn(new FocusResultState(true, false));

        // When
        Photo result = resultOf(testee);

        // Then
        InOrder inOrder = inOrder(cameraDevice);
        inOrder.verify(cameraDevice).autoFocus();
        inOrder.verify(cameraDevice).takePicture();
        inOrder.verify(cameraDevice).startPreview();

        assertEquals(result, PHOTO);
    }
}