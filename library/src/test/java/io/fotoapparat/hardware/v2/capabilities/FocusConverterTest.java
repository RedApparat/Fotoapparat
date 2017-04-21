package io.fotoapparat.hardware.v2.capabilities;

import android.hardware.camera2.CameraMetadata;

import org.junit.Test;

import io.fotoapparat.hardware.v2.parameters.converters.FocusConverter;
import io.fotoapparat.parameter.FocusMode;

import static junit.framework.Assert.assertEquals;

@SuppressWarnings("NewApi")
public class FocusConverterTest {

    @Test
    public void invalidToFocusConversion_fixedDefault() throws Exception {
        // When
        FocusMode focusMode = FocusConverter.afModeToFocus(-1);

        // Then
        assertEquals(FocusMode.FIXED, focusMode);
    }

    @Test
    public void afAutoMode_FocusAutoMode() throws Exception {
        // When
        FocusMode focusMode = FocusConverter.afModeToFocus(CameraMetadata.CONTROL_AF_MODE_AUTO);

        // Then
        assertEquals(FocusMode.AUTO, focusMode);
    }

    @Test
    public void invalidToAfModeConversion_fixedDefault() throws Exception {

        // When
        int focusMode = FocusConverter.focusToAfMode(null);

        // Then
        assertEquals(CameraMetadata.CONTROL_AF_MODE_OFF, focusMode);
    }

    @Test
    public void edofFocusMode_edofAutoMode() throws Exception {
        // Given

        // When
        FocusMode focusMode = FocusConverter.afModeToFocus(CameraMetadata.CONTROL_AF_MODE_EDOF);

        // Then
        assertEquals(FocusMode.EDOF, focusMode);
    }

}