package io.fotoapparat.parameter.selector;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import io.fotoapparat.parameter.Size;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class SizeSelectorsTest {

    @Test
    public void biggestSize() throws Exception {
        // Given
        List<Size> availableSizes = asList(
                new Size(4032, 3024),
                new Size(7680, 4320),
                new Size(1280, 720)
        );

        // When
        Size result = SizeSelectors
                .biggestSize()
                .select(availableSizes);

        // Then
        assertEquals(
                new Size(7680, 4320),
                result
        );
    }

    @Test
    public void bigestSize_EmptyList() throws Exception {
        // When
        Size result = SizeSelectors
                .biggestSize()
                .select(Collections.<Size>emptyList());

        // Then
        assertNull(result);
    }

    @Test
    public void smallestSize() throws Exception {
        // Given
        List<Size> availableSizes = asList(
                new Size(4032, 3024),
                new Size(7680, 4320),
                new Size(1280, 720)
        );

        // When
        Size result = SizeSelectors
                .smallestSize()
                .select(availableSizes);

        // Then
        assertEquals(
                new Size(1280, 720),
                result
        );
    }

    @Test
    public void smallestSize_EmptyList() throws Exception {
        // When
        Size result = SizeSelectors
                .smallestSize()
                .select(Collections.<Size>emptyList());

        // Then
        assertNull(result);
    }

}