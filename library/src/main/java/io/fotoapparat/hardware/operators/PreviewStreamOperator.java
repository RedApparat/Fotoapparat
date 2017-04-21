package io.fotoapparat.hardware.operators;

import io.fotoapparat.preview.PreviewStream;

/**
 * Provides {@link PreviewStream} controlled by camera.
 */
public interface PreviewStreamOperator {

    /**
     * @return {@link PreviewStream} associated with camera.
     */
    PreviewStream getPreviewStream();

}
