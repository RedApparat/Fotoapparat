package io.fotoapparat.preview;

import java.util.Arrays;

import io.fotoapparat.parameter.Size;

/**
 * Frame of the preview stream.
 */
public class Frame {

    /**
     * Size of the frame in pixels (before rotation).
     */
    public final Size size;

    /**
     * Image in NV21 format.
     */
    public final byte[] image;

    /**
     * Clockwise rotation of the image in degrees relatively to user.
     */
    public final int rotation;

    public Frame(Size size, byte[] image, int rotation) {
        this.size = size;
        this.image = image;
        this.rotation = rotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Frame frame = (Frame) o;

        return rotation == frame.rotation
                && size.equals(frame.size)
                && Arrays.equals(image, frame.image);
    }

    @Override
    public int hashCode() {
        int result = size.hashCode();
        result = 31 * result + Arrays.hashCode(image);
        result = 31 * result + rotation;
        return result;
    }

    @Override
    public String toString() {
        return "Frame{" +
                "size=" + size +
                ", image= array(" + image.length + ")" +
                ", rotation=" + rotation +
                '}';
    }

}
