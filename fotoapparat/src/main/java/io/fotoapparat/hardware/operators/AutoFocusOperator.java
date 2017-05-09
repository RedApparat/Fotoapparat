package io.fotoapparat.hardware.operators;

import io.fotoapparat.lens.FocusResult;

/**
 * Performs auto focus.
 */
public interface AutoFocusOperator {

    /**
     * Performs auto focus. This is a blocking operation which returns the result of the operation
     * when auto focus completes.
     */
    FocusResult autoFocus();

}
