package io.fotoapparat.lens;

/**
 * The result of an attempt to lock the focus.
 */
public class FocusResult {

    /**
     * {@code True} if the camera succeeded to lock the focus.
     */
    public final boolean succeeded;

    /**
     * {@code True} if the camera needs to perform an exposure measurement.
     */
    public final boolean needsExposureMeasurement;

    public FocusResult(boolean succeeded, boolean needsExposureMeasurement) {
        this.succeeded = succeeded;
        this.needsExposureMeasurement = needsExposureMeasurement;
    }

    /**
     * Creates a new instance which has neither succeeded nor needs exposure measurement.
     *
     * @return A new, invalid {@link FocusResult}
     */
    public static FocusResult none() {
        return new FocusResult(false, false);
    }

    /**
     * Creates a new instance which has succeeded but doesn't need exposure measurement.
     *
     * @return A new {@link FocusResult}
     */
    public static FocusResult successNoMeasurement() {
        return new FocusResult(true, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FocusResult that = (FocusResult) o;

        return succeeded == that.succeeded
                && needsExposureMeasurement == that.needsExposureMeasurement;
    }

    @Override
    public int hashCode() {
        int result = (succeeded ? 1 : 0);
        result = 31 * result + (needsExposureMeasurement ? 1 : 0);
        return result;
    }
}
