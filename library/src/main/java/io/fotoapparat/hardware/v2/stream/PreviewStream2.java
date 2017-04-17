package io.fotoapparat.hardware.v2.stream;

import android.support.annotation.NonNull;

import java.util.LinkedHashSet;
import java.util.Set;

import io.fotoapparat.preview.Frame;
import io.fotoapparat.preview.FrameProcessor;
import io.fotoapparat.preview.PreviewStream;

/**
 * {@link PreviewStream} of Camera v2.
 */
@SuppressWarnings("NewApi")
public class PreviewStream2 implements PreviewStream,
		OnImageAcquiredObserver.OnFrameAcquiredListener {

	private final Set<FrameProcessor> frameProcessors = new LinkedHashSet<>();
	private final OnImageAcquiredObserver surfaceReader;

	public PreviewStream2(OnImageAcquiredObserver surfaceReader) {
		this.surfaceReader = surfaceReader;
	}

	@Override
	public void addFrameToBuffer() {
		// Does nothing
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
		surfaceReader.setListener(this);
	}

	@Override
	public void onFrameAcquired(byte[] bytes) {
		synchronized (frameProcessors) {
			dispatchFrame(bytes);
		}
	}

	private void dispatchFrame(byte[] image) {
		final Frame frame = new Frame(image, 0); // TODO provide rotation?

		for (FrameProcessor frameProcessor : frameProcessors) {
			frameProcessor.processFrame(frame);
		}
	}
}
