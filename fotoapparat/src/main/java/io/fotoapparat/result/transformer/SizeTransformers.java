package io.fotoapparat.result.transformer;

import io.fotoapparat.parameter.Size;

/**
 * Implementations of {@link Transformer} for sizes.
 */
public class SizeTransformers {

    /**
     * @return {@link Transformer} which always returns the same size as it receives.
     */
    public static Transformer<Size, Size> originalSize() {
        return new Transformer<Size, Size>() {
            @Override
            public Size transform(Size input) {
                return input;
            }
        };
    }

    /**
     * @param scaleFactor scale factor which would be applied to image.
     * @return {@link Transformer} which returns size scaled by given factor.
     */
    public static Transformer<Size, Size> scaled(final float scaleFactor) {
        return new Transformer<Size, Size>() {
            @Override
            public Size transform(Size input) {
                return new Size(
                        (int) (input.width * scaleFactor),
                        (int) (input.height * scaleFactor)
                );
            }
        };
    }

}
