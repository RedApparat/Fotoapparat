package io.fotoapparat.parameter.selector;

import org.junit.Test;

import java.util.Set;

import io.fotoapparat.parameter.FocusMode;

import static io.fotoapparat.test.TestUtils.asSet;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class FocusModeSelectorsTest {

    @Test
    public void focusMode_Available() throws Exception {
        // Given
        Set<FocusMode> availableModes = asSet(
                FocusMode.AUTO,
                FocusMode.CONTINUOUS_FOCUS,
                FocusMode.FIXED
        );

        // When
        FocusMode result = FocusModeSelectors
                .continuousFocus()
                .select(availableModes);

        // Then
        assertEquals(
                FocusMode.CONTINUOUS_FOCUS,
                result
        );
    }

    @Test
    public void focusMode_NotAvailable() throws Exception {
        // Given
        Set<FocusMode> availableModes = asSet(
                FocusMode.AUTO,
                FocusMode.FIXED
        );

        // When
        FocusMode result = FocusModeSelectors
                .continuousFocus()
                .select(availableModes);

        // Then
        assertNull(result);
    }

}