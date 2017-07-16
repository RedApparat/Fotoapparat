package io.fotoapparat.parameter.selector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import io.fotoapparat.parameter.ScaleType;

import static junit.framework.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ScaleTypeSelectorsTest {


    @Test
    public void select_crop() throws Exception {
        // When
        ScaleType result = ScaleTypeSelectors
                .centerCropped();

        // Then
        assertEquals(
                ScaleType.CENTER_CROP,
                result
        );
    }

    @Test
    public void select_centerInside() throws Exception {
        // When
        ScaleType result = ScaleTypeSelectors
                .centerInside();

        // Then
        assertEquals(
                ScaleType.CENTER_INSIDE,
                result
        );
    }
}