package io.fotoapparat.parameter;

/**
 * Size in arbitrary units. Immutable.
 */
public class Size {

    public final int width;
    public final int height;

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the aspect ratio for this size. -1 if invalid dimensions.
     *
     * @return The aspect ratio.
     */
    public float getAspectRatio() {
        if (width == 0 || height == 0) {
            return Float.NaN;
        }
        return (float) width / height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Size size = (Size) o;

        return width == size.width && height == size.height;

    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        return result;
    }

    @Override
    public String toString() {
        return "Size{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }

    /**
     * @return new instance of {@link Size} with width and height being swapped.
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public Size flip() {
        return new Size(height, width);
    }
}
