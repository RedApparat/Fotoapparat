package io.fotoapparat.hardware.v1;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.support.annotation.NonNull;

import java.util.LinkedHashSet;
import java.util.Set;

import io.fotoapparat.preview.Frame;
import io.fotoapparat.preview.FrameProcessor;
import io.fotoapparat.preview.PreviewStream;

/**
 * {@link PreviewStream} of Camera v1.
 */
@SuppressWarnings("deprecation")
public class PreviewStream1 implements PreviewStream {

	private static final int BYTES_PER_PIXEL = ImageFormat.getBitsPerPixel(ImageFormat.NV21) / 8;

	private final Camera camera;

	private final Set<FrameProcessor> frameProcessors = new LinkedHashSet<>();

	public PreviewStream1(Camera camera) {
		this.camera = camera;
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

		return new byte[previewSize.width * previewSize.height * BYTES_PER_PIXEL];
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
				synchronized (frameProcessors) {
					dispatchFrame(data);
				}
			}
		});
	}

	private void dispatchFrame(byte[] image) {
		final Frame frame = new Frame(image, 0); // TODO provide rotation

		for (final FrameProcessor frameProcessor : frameProcessors) {
			frameProcessor.processFrame(frame);
		}

		returnFrameToBuffer(frame);
	}

	private void returnFrameToBuffer(Frame frame) {
		camera.addCallbackBuffer(
				frame.image
		);
	}

}
