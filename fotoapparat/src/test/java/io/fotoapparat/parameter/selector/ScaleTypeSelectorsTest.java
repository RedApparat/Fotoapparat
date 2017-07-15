package io.fotoapparat.parameter.selector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.fotoapparat.parameter.ScaleType;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ScaleTypeSelectorsTest {


    @Test
    public void select_crop() throws Exception {
        // Given
        List<ScaleType> availableScaleTypes = asList(
                ScaleType.CENTER_CROP,
                ScaleType.CENTER_INSIDE
        );

        // When
        ScaleType result = ScaleTypeSelectors
                .centerCropped()
                .select(availableScaleTypes);

        // Then
        assertEquals(
                ScaleType.CENTER_CROP,
                result
        );
    }

    @Test
    public void select_centerInside() throws Exception {
        // Given
        List<ScaleType> availableScaleTypes = asList(
                ScaleType.CENTER_CROP,
                ScaleType.CENTER_INSIDE
        );

        // When
        ScaleType result = ScaleTypeSelectors
                .centerInside()
                .select(availableScaleTypes);

        // Then
        assertEquals(
                ScaleType.CENTER_INSIDE,
                result
        );
    }
}