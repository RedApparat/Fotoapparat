package io.fotoapparat.hardware.v2.stream;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import io.fotoapparat.hardware.v2.stream.OnImageAcquiredObserver.OnFrameAcquiredListener;
import io.fotoapparat.preview.Frame;
import io.fotoapparat.preview.FrameProcessor;

import static junit.framework.Assert.assertEquals;

public class PreviewStream2Test {

	@Test
	public void acquireFrame() throws Exception {
		// Given
		final AtomicReference<OnFrameAcquiredListener> listenerReference = new AtomicReference<>();
		final AtomicReference<Frame> frameReference = new AtomicReference<>();
		final CountDownLatch listenerSet = new CountDownLatch(1);
		final CountDownLatch frameAcquired = new CountDownLatch(1);

		PreviewStream2 testee = new PreviewStream2(new OnImageAcquiredObserver() {
			@Override
			public void setListener(OnFrameAcquiredListener listener) {
				listenerReference.set(listener);
				listenerSet.countDown();
			}
		});

		testee.addProcessor(new FrameProcessor() {
			@Override
			public void processFrame(Frame frame) {
				frameReference.set(frame);
				frameAcquired.countDown();
			}
		});
		testee.start();

		// When
		listenerSet.await();
		listenerReference.get().onFrameAcquired(new byte[]{1});

		// Then
		frameAcquired.await();
		assertEquals(new Frame(new byte[]{1}, 0), frameReference.get());
	}
}