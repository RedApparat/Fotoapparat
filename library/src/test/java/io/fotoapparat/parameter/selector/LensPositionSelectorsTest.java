package io.fotoapparat.parameter.selector;

import org.junit.Test;

import java.util.List;

import io.fotoapparat.parameter.LensPosition;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LensPositionSelectorsTest {

    @Test
    public void front_Available() throws Exception {
        // Given
        List<LensPosition> availablePositions = asList(
                LensPosition.BACK,
                LensPosition.FRONT,
                LensPosition.EXTERNAL
        );

        // When
        LensPosition result = LensPositionSelectors
                .front()
                .select(availablePositions);

        // Then
        assertEquals(
                LensPosition.FRONT,
                result
        );
    }

    @Test
    public void front_NotAvailable() throws Exception {
        // Given
        List<LensPosition> availablePositions = asList(
                LensPosition.BACK,
                LensPosition.EXTERNAL
        );

        // When
        LensPosition result = LensPositionSelectors
                .front()
                .select(availablePositions);

        // Then
        assertNull(result);
    }

    @Test
    public void back_Available() throws Exception {
        // Given
        List<LensPosition> availablePositions = asList(
                LensPosition.BACK,
                LensPosition.FRONT,
                LensPosition.EXTERNAL
        );

        // When
        LensPosition result = LensPositionSelectors
                .back()
                .select(availablePositions);

        // Then
        assertEquals(
                LensPosition.BACK,
                result
        );
    }

    @Test
    public void back_NotAvailable() throws Exception {
        // Given
        List<LensPosition> availablePositions = asList(
                LensPosition.FRONT,
                LensPosition.EXTERNAL
        );

        // When
        LensPosition result = LensPositionSelectors
                .back()
                .select(availablePositions);

        // Then
        assertNull(result);
    }

    @Test
    public void external_Available() throws Exception {
        // Given
        List<LensPosition> availablePositions = asList(
                LensPosition.BACK,
                LensPosition.FRONT,
                LensPosition.EXTERNAL
        );

        // When
        LensPosition result = LensPositionSelectors
                .external()
                .select(availablePositions);

        // Then
        assertEquals(
                LensPosition.EXTERNAL,
                result
        );
    }

    @Test
    public void external_NotAvailable() throws Exception {
        // Given
        List<LensPosition> availablePositions = asList(
                LensPosition.FRONT,
                LensPosition.BACK
        );

        // When
        LensPosition result = LensPositionSelectors
                .external()
                .select(availablePositions);

        // Then
        assertNull(result);
    }

}