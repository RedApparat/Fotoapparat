package io.fotoapparat.hardware.operators;

import io.fotoapparat.result.FocusResultState;

/**
 * Performs auto focus.
 */
public interface AutoFocusOperator {

    /**
     * Performs auto focus. This is a blocking operation which returns the result of the operation
     * when auto focus completes.
     */
    FocusResultState autoFocus();

}
