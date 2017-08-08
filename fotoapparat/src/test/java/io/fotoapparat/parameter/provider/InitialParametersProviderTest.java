package io.fotoapparat.parameter.provider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;

import io.fotoapparat.parameter.Size;

import static io.fotoapparat.test.TestUtils.asSet;
import static io.fotoapparat.util.TestSelectors.select;
import static junit.framework.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class InitialParametersProviderTest {

    static final Size PHOTO_SIZE = new Size(4000, 3000);
    static final Size PREVIEW_SIZE = new Size(2000, 1500);
    static final Size PREVIEW_SIZE_WRONG_ASPECT_RATIO = new Size(1000, 1000);

    static final Set<Size> ALL_PREVIEW_SIZES = asSet(
            PREVIEW_SIZE,
            PREVIEW_SIZE_WRONG_ASPECT_RATIO
    );

    @Test
    public void validPreviewSizeSelector_WithValidAspectRatio() throws Exception {
        // When
        Size result = InitialParametersProvider
                .validPreviewSizeSelector(
                        PHOTO_SIZE,
                        select(PREVIEW_SIZE)
                )
                .select(ALL_PREVIEW_SIZES);

        // Then
        assertEquals(
                PREVIEW_SIZE,
                result
        );
    }

    @Test
    public void validPreviewSizeSelector_NoPreviewSizeWithSameAspectRatio() throws Exception {
        // Given
        Size photoSize = new Size(10000, 100);

        // When
        Size result = InitialParametersProvider
                .validPreviewSizeSelector(
                        photoSize,
                        select(PREVIEW_SIZE)
                )
                .select(ALL_PREVIEW_SIZES);

        // Then
        assertEquals(
                PREVIEW_SIZE,
                result
        );
    }

}