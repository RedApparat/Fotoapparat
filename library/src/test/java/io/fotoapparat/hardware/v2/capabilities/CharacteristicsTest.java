package io.fotoapparat.hardware.v2.capabilities;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
@RunWith(MockitoJUnitRunner.class)
public class CharacteristicsTest {

    @Mock
    CameraCharacteristics cameraCharacteristics;
    @Mock
    CameraManager cameraManager;
    @InjectMocks
    Characteristics testee;

    @Before
    public void setUp() throws Exception {
        given(cameraManager.getCameraCharacteristics(anyString()))
                .willReturn(cameraCharacteristics);
    }

    @Test
    public void isFlashAvailable() throws Exception {
        // Given
        given(cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE))
                .willReturn(true);

        testee.setCameraId("1");

        // When
        boolean flashAvailable = testee.isFlashAvailable();

        // Then
        assertTrue(flashAvailable);
    }

    @Test
    public void isLegacyDevice() throws Exception {
        // Given
        given(cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL))
                .willReturn(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY);

        testee.setCameraId("1");

        // When
        boolean isLegacyDevice = testee.isLegacyDevice();

        // Then
        assertTrue(isLegacyDevice);
    }

    @Test
    public void sensorOrientation() throws Exception {
        // Given
        given(cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION))
                .willReturn(90);

        testee.setCameraId("1");

        // When
        int sensorOrientation = testee.getSensorOrientation();

        // Then
        assertEquals(90, sensorOrientation);
    }

    @Test
    public void autoExposureModes() throws Exception {
        // Given
        int[] availableExposures = {
                CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH_REDEYE,
                CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH,
                CameraMetadata.CONTROL_AE_MODE_OFF
        };
        given(cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES))
                .willReturn(availableExposures);

        testee.setCameraId("1");

        // When
        int[] autoExposureModes = testee.autoExposureModes();

        // Then
        assertEquals(availableExposures, autoExposureModes);
    }

    @Test
    public void autoFocusModes() throws Exception {
        // Given
        int[] availableExposures = {
                CameraMetadata.CONTROL_AF_MODE_CONTINUOUS_VIDEO,
                CameraMetadata.CONTROL_AF_MODE_EDOF,
                CameraMetadata.CONTROL_AF_MODE_OFF
        };
        given(cameraCharacteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES))
                .willReturn(availableExposures);

        testee.setCameraId("1");

        // When
        int[] autoFocusModes = testee.autoFocusModes();

        // Then
        assertEquals(availableExposures, autoFocusModes);
    }

}