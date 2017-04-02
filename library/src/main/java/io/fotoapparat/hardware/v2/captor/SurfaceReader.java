package io.fotoapparat.hardware.v2.captor;

import android.graphics.ImageFormat;
import android.hardware.camera2.CameraCharacteristics;
import android.media.ImageReader;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Size;
import android.view.Surface;

import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.hardware.v2.capabilities.CameraCapabilities;
import io.fotoapparat.hardware.v2.capabilities.SizeCapability;

/**
 * Reads the
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SurfaceReader {

	private final CameraCapabilities cameraCapabilities;
	private ImageReader imageReader;

	public SurfaceReader(CameraCapabilities cameraCapabilities) {
		this.cameraCapabilities = cameraCapabilities;
	}

	public Surface getSurface() {
		CameraCharacteristics cameraCharacteristics = cameraCapabilities.getCameraCharacteristics();

		SizeCapability sizeCapability = new SizeCapability(cameraCharacteristics);
		Size largestSize = sizeCapability.getLargestSize();

		imageReader = ImageReader
				.newInstance(largestSize.getWidth(),
						largestSize.getHeight(),
						ImageFormat.JPEG,
						1
				);
		return imageReader.getSurface();
	}

	public byte[] getPhotoBytes() {
		ImageAvailabilityAction listener = new ImageAvailabilityAction();

		imageReader.setOnImageAvailableListener(
				listener,
				CameraThread
						.getInstance()
						.createHandler()
		);

		return listener.getBytes();
	}
}
