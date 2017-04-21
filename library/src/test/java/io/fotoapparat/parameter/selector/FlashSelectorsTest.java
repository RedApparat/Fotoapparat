package io.fotoapparat.parameter.selector;

import org.junit.Test;

import java.util.Set;

import io.fotoapparat.parameter.Flash;

import static io.fotoapparat.test.TestUtils.asSet;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class FlashSelectorsTest {

    @Test
    public void focusMode_Available() throws Exception {
        // Given
        Set<Flash> availableModes = asSet(
                Flash.AUTO,
                Flash.AUTO_RED_EYE,
                Flash.OFF
        );

        // When
        Flash result = FlashSelectors
                .autoRedEye()
                .select(availableModes);

        // Then
        assertEquals(
                Flash.AUTO_RED_EYE,
                result
        );
    }

    @Test
    public void focusMode_NotAvailable() throws Exception {
        // Given
        Set<Flash> availableModes = asSet(
                Flash.AUTO,
                Flash.OFF
        );

        // When
        Flash result = FlashSelectors
                .autoRedEye()
                .select(availableModes);

        // Then
        assertNull(result);
    }

}