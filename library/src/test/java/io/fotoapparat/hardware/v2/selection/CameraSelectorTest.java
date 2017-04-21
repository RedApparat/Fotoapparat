package io.fotoapparat.hardware.v2.selection;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.parameter.LensPosition;

import static android.hardware.camera2.CameraAccessException.CAMERA_ERROR;
import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@SuppressWarnings("NewApi")
@RunWith(MockitoJUnitRunner.class)
public class CameraSelectorTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    CameraManager manager;
    @InjectMocks
    CameraSelector testee;

    @Before
    public void setUp() throws Exception {
        given(manager.getCameraCharacteristics("0").get(CameraCharacteristics.LENS_FACING))
                .willReturn(CameraMetadata.LENS_FACING_BACK);

        given(manager.getCameraCharacteristics("1").get(CameraCharacteristics.LENS_FACING))
                .willReturn(CameraMetadata.LENS_FACING_FRONT);
    }

    @Test(expected = CameraException.class)
    public void cameraNotAvailable() throws Exception {
        // Given
        given(manager.getCameraIdList())
                .willThrow(new CameraAccessException(CAMERA_ERROR));

        // When
        testee.findCameraId(LensPosition.EXTERNAL);

        // Then
        // exception
    }

    @Test(expected = CameraException.class)
    public void noCameraFound() throws Exception {
        // Given
        given(manager.getCameraIdList())
                .willReturn(new String[]{"0"});

        // When
        testee.findCameraId(LensPosition.EXTERNAL);

        // Then
        // exception
    }

    @Test
    public void getFrontCamera() throws Exception {
        // Given
        given(manager.getCameraIdList())
                .willReturn(new String[]{"0", "1"});

        // When
        String cameraId = testee.findCameraId(LensPosition.FRONT);

        // Then
        assertEquals("1", cameraId);
    }

}