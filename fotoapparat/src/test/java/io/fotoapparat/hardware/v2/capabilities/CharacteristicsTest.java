package io.fotoapparat.hardware.v2.capabilities;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
@RunWith(MockitoJUnitRunner.class)
public class CharacteristicsTest {

    @Mock
    CameraCharacteristics cameraCharacteristics;
    @Mock
    StreamConfigurationMap streamConfigurationMap;

    @InjectMocks
    Characteristics testee;

    @Test
    public void isFrontFacingLens() throws Exception {
        // Given
        given(cameraCharacteristics.get(CameraCharacteristics.LENS_FACING))
                .willReturn(CameraMetadata.LENS_FACING_FRONT);

        // When
        boolean flashAvailable = testee.isFrontFacingLens();

        // Then
        assertTrue(flashAvailable);
    }

    @Test
    public void isFlashAvailable() throws Exception {
        // Given
        given(cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE))
                .willReturn(true);

        // When
        boolean flashAvailable = testee.isFlashAvailable();

        // Then
        assertTrue(flashAvailable);
    }

    @Test
    public void isFixedFocus() throws Exception {
        // Given
        given(cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE))
                .willReturn(0f);

        // When
        boolean fixedFocusLens = testee.isFixedFocusLens();

        // Then
        assertTrue(fixedFocusLens);
    }

    @Test
    public void isLegacyDevice() throws Exception {
        // Given
        given(cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL))
                .willReturn(CameraMetadata.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY);

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

        // When
        int[] autoFocusModes = testee.autoFocusModes();

        // Then
        assertEquals(availableExposures, autoFocusModes);
    }

}