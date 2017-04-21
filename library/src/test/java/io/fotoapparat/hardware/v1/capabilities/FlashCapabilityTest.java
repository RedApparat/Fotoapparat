package io.fotoapparat.hardware.v1.capabilities;

import android.hardware.Camera;

import org.junit.Test;

import io.fotoapparat.parameter.Flash;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("deprecation")
public class FlashCapabilityTest {

    @Test
    public void toFlash() throws Exception {
        // When
        Flash flash = FlashCapability.toFlash(
                Camera.Parameters.FLASH_MODE_AUTO
        );

        // Then
        assertEquals(
                Flash.AUTO,
                flash
        );
    }

    @Test
    public void toFlash_Unknown() throws Exception {
        // When
        Flash flash = FlashCapability.toFlash("whatever");

        // Then
        assertEquals(
                Flash.OFF,
                flash
        );
    }

    @Test
    public void toCode() throws Exception {
        // When
        String code = FlashCapability.toCode(Flash.AUTO);

        // Then
        assertEquals(
                Camera.Parameters.FLASH_MODE_AUTO,
                code
        );
    }
}