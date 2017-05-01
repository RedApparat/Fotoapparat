package io.fotoapparat.lens;

/**
 * The result of an attempt to lock the focus.
 */
public enum FocusResultState {

    /**
     * The camera's lens does not support focus operation.
     */
    NOT_SUPPORTED_OPERATION,

    /**
     * The camera failed to lock the focus.
     */
    FAILURE,

    /**
     * The camera succeeded to lock the focus.
     */
    SUCCESS,

    /**
     * The camera succeeded to lock the focus but needs to perform an exposure measurement.
     */
    SUCCESS_NEEDS_EXPOSURE_MEASUREMENT

}
