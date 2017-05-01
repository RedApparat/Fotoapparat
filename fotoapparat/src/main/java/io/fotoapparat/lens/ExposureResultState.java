package io.fotoapparat.lens;

/**
 * The result of an attempt to gather exposure data.
 */
public enum ExposureResultState {

    /**
     * The camera failed to gather exposure data.
     */
    FAILURE,

    /**
     * The camera succeeded to gather exposure data.
     */
    SUCCESS

}
