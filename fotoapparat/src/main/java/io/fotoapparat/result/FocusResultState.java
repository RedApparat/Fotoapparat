package io.fotoapparat.result;

/**
 * The result of an attempt to lock the focus.
 */
public enum FocusResultState {

    /**
     * The camera failed to lock the focus.
     */
    FAILUTE,

    /**
     * The camera succeeded to lock the focus.
     */
    SUCCESS,

    /**
     * The camera succeeded to lock the focus but needs to perform an exposure measurement.
     */
    SUCCESS_NEEDS_EXPOSURE_MEASUREMENT

}
