package io.fotoapparat.hardware.v1.capabilities;

import android.hardware.Camera;

import org.junit.Test;

import io.fotoapparat.parameter.FocusMode;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("deprecation")
public class FocusConverterTest {

    @Test
    public void toFocusMode() throws Exception {
        // When
        FocusMode focusMode = FocusCapability.toFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

        // Then
        assertEquals(
                FocusMode.AUTO,
                focusMode
        );
    }

    @Test
    public void toFocusMode_UnknownCode() throws Exception {
        // When
        FocusMode focusMode = FocusCapability.toFocusMode("whatever");

        // Then
        assertEquals(
                FocusMode.FIXED,
                focusMode
        );
    }

    @Test
    public void toCode() throws Exception {
        // When
        String code = FocusCapability.toCode(FocusMode.INFINITY);

        // Then
        assertEquals(
                Camera.Parameters.FOCUS_MODE_INFINITY,
                code
        );
    }

}