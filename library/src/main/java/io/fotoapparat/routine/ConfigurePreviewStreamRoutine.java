package io.fotoapparat.routine;

import io.fotoapparat.hardware.CameraDevice;
import io.fotoapparat.preview.FrameProcessor;
import io.fotoapparat.preview.PreviewStream;

/**
 * Configures {@link PreviewStream} of the camera.
 */
public class ConfigurePreviewStreamRoutine implements Runnable {

    private final CameraDevice cameraDevice;
    private final FrameProcessor frameProcessor;

    public ConfigurePreviewStreamRoutine(CameraDevice cameraDevice,
                                         FrameProcessor frameProcessor) {
        this.cameraDevice = cameraDevice;
        this.frameProcessor = frameProcessor;
    }

    @Override
    public void run() {
        if (frameProcessor == null) {
            return;
        }

        PreviewStream previewStream = cameraDevice.getPreviewStream();

        previewStream.addProcessor(frameProcessor);
        previewStream.start();
    }

}
