package io.fotoapparat.lens;

/**
 * The result of an attempt to lock the focus.
 */
public class FocusResultState {

    /**
     * {@code True} if the camera succeeded to lock the focus.
     */
    public final boolean succeeded;
    /**
     * {@code True} if the camera needs to perform an exposure measurement.
     */
    public final boolean needsExposureMeasurement;

    public FocusResultState(boolean succeeded, boolean needsExposureMeasurement) {
        this.succeeded = succeeded;
        this.needsExposureMeasurement = needsExposureMeasurement;
    }

    public static FocusResultState none() {
        return new FocusResultState(false, false);
    }
}
