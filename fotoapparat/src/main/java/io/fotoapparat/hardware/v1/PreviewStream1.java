package io.fotoapparat.hardware.v1;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.support.annotation.NonNull;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.fotoapparat.parameter.Size;
import io.fotoapparat.preview.Frame;
import io.fotoapparat.preview.FrameProcessor;
import io.fotoapparat.preview.PreviewStream;

/**
 * {@link PreviewStream} of Camera v1.
 */
@SuppressWarnings("deprecation")
public class PreviewStream1 implements PreviewStream {

    private static Executor FRAME_PROCESSORS_EXECUTOR = Executors.newSingleThreadExecutor();

    private final Camera camera;

    private final Set<FrameProcessor> frameProcessors = new LinkedHashSet<>();

    private Size previewSize = null;
    private int frameOrientation = 0;

    public PreviewStream1(Camera camera) {
        this.camera = camera;
    }

    /**
     * @param frameOrientation CW rotation of frames in degrees.
     */
    public void setFrameOrientation(int frameOrientation) {
        this.frameOrientation = frameOrientation;
    }

    @Override
    public void addFrameToBuffer() {
        camera.addCallbackBuffer(
                allocateBuffer(camera.getParameters())
        );
    }

    private byte[] allocateBuffer(Camera.Parameters parameters) {
        ensureNv21Format(parameters);

        Camera.Size previewSize = parameters.getPreviewSize();
        this.previewSize = new Size(
                previewSize.width,
                previewSize.height
        );

        return new byte[bytesPerFrame(previewSize)];
    }

    private int bytesPerFrame(Camera.Size previewSize) {
        return (previewSize.width * previewSize.height * ImageFormat.getBitsPerPixel(ImageFormat.NV21)) / 8;
    }

    private void ensureNv21Format(Camera.Parameters parameters) {
        if (parameters.getPreviewFormat() != ImageFormat.NV21) {
            throw new UnsupportedOperationException("Only NV21 preview format is supported");
        }
    }

    @Override
    public void addProcessor(@NonNull FrameProcessor processor) {
        synchronized (frameProcessors) {
            frameProcessors.add(processor);
        }
    }

    @Override
    public void removeProcessor(@NonNull FrameProcessor processor) {
        synchronized (frameProcessors) {
            frameProcessors.remove(processor);
        }
    }

    @Override
    public void start() {
        addFrameToBuffer();

        camera.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                dispatchFrameOnBackgroundThread(data);
            }
        });
    }

    private void dispatchFrameOnBackgroundThread(final byte[] data) {
        FRAME_PROCESSORS_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (frameProcessors) {
                    dispatchFrame(data);
                }
            }
        });
    }

    private void dispatchFrame(byte[] image) {
        ensurePreviewSizeAvailable();

        final Frame frame = new Frame(previewSize, image, frameOrientation);

        for (final FrameProcessor frameProcessor : frameProcessors) {
            frameProcessor.processFrame(frame);
        }

        returnFrameToBuffer(frame);
    }

    private void ensurePreviewSizeAvailable() {
        if (previewSize == null) {
            throw new IllegalStateException("previewSize is null. Frame was not added?");
        }
    }

    private void returnFrameToBuffer(Frame frame) {
        camera.addCallbackBuffer(
                frame.image
        );
    }

}
