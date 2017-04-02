package io.fotoapparat.hardware.v2.captor;

import android.graphics.ImageFormat;
import android.hardware.camera2.CameraCharacteristics;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Size;
import android.view.Surface;

import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;

import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.hardware.v2.capabilities.CameraCapabilities;
import io.fotoapparat.hardware.v2.capabilities.SizeCapability;

/**
 * Creates a {@link Surface} which can capture single events.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SurfaceReader implements ImageReader.OnImageAvailableListener {

	private final CameraCapabilities cameraCapabilities;
	private ImageReader imageReader;

	private final CountDownLatch countDownLatch = new CountDownLatch(1);
	private byte[] bytes;

	public SurfaceReader(CameraCapabilities cameraCapabilities) {
		this.cameraCapabilities = cameraCapabilities;
	}

	@Override
	public void onImageAvailable(ImageReader reader) {
		Image image = reader.acquireNextImage();
		ByteBuffer buffer = image.getPlanes()[0].getBuffer();
		bytes = new byte[buffer.remaining()];
		countDownLatch.countDown();
	}

	/**
	 * Returns a {@link Surface} which can be used as a target for still capture events.
	 *
	 * @return the new Surface
	 */
	public Surface getSurface() {
		if (imageReader == null) {
			createImageReader();
		}
		return imageReader.getSurface();
	}

	/**
	 * Returns the next available Image as a byte array.
	 *
	 * @return the Image as byte array.
	 */
	public byte[] getPhotoBytes() {
		imageReader.setOnImageAvailableListener(
				this,
				CameraThread
						.getInstance()
						.createHandler()
		);

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// Do nothing
		}

		return bytes;
	}

	private void createImageReader() {
		CameraCharacteristics cameraCharacteristics = cameraCapabilities.getCameraCharacteristics();

		SizeCapability sizeCapability = new SizeCapability(cameraCharacteristics);
		Size largestSize = sizeCapability.getLargestSize();

		imageReader = ImageReader
				.newInstance(
						largestSize.getWidth(),
						largestSize.getHeight(),
						ImageFormat.JPEG,
						1
				);
	}
}
