package io.fotoapparat.result

/**
 * The result of an attempt to lock the focus.
 */
sealed class FocusResult {

    /**
     * Camera is unable to focus for some reason.
     */
    object UnableToFocus : FocusResult()

    /**
     * Camera is focused successfully.
     */
    object Focused : FocusResult()

}