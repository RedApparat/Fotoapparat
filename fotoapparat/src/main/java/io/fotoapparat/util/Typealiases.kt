package io.fotoapparat.util

import io.fotoapparat.preview.Frame

/**
 * Performs processing on preview frames.
 *
 * This function is called from worker thread (aka non-UI thread). After
 * the function completes the frame is returned back to the pool where it is reused
 * afterwards. Thus, implementations should take special care to not do any operations on
 * frame after method completes.
 *
 * @param frame frame of the preview. Do not cache it as it will eventually be reused by the
 *              camera.
 */
typealias FrameProcessor = (frame: Frame) -> Unit
