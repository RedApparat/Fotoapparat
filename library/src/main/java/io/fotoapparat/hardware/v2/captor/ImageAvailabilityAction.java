package io.fotoapparat.hardware.v2.captor;

import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;

/**
 * A callback triggered when a photo is available on the {@link ImageReader}.
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
class ImageAvailabilityAction implements ImageReader.OnImageAvailableListener {

	private final CountDownLatch countDownLatch = new CountDownLatch(1);
	private byte[] bytes;

	@Override
	public void onImageAvailable(ImageReader reader) {
		Image image = reader.acquireNextImage();
		ByteBuffer buffer = image.getPlanes()[0].getBuffer();
		bytes = new byte[buffer.remaining()];
		countDownLatch.countDown();
	}

	/**
	 * @return a byte array of the image when it's available.
	 */
	byte[] getBytes() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// Do nothing
		}

		return bytes;
	}
}
