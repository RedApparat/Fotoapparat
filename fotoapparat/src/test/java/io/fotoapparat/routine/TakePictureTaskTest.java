package io.fotoapparat.routine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.photo.Photo;

import static io.fotoapparat.test.TestUtils.resultOf;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
public class TakePictureTaskTest {

    static final Photo PHOTO = Photo.empty();

    @Mock
    CameraDevice cameraDevice;

    @InjectMocks
    TakePictureTask testee;

    @Test
    public void takePhoto() throws Exception {
        // Given
        given(cameraDevice.takePicture())
                .willReturn(PHOTO);

        // When
        Photo result = resultOf(testee);

        // Then
        InOrder inOrder = inOrder(cameraDevice);
        inOrder.verify(cameraDevice).autoFocus();
        inOrder.verify(cameraDevice).takePicture();

        assertEquals(result, PHOTO);
    }

}