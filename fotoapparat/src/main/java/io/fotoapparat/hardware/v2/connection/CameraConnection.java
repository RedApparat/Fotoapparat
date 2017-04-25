package io.fotoapparat.hardware.v2.connection;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.concurrent.CountDownLatch;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.operators.ConnectionOperator;
import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.hardware.v2.capabilities.Characteristics;
import io.fotoapparat.hardware.v2.selection.CameraSelector;
import io.fotoapparat.parameter.LensPosition;

/**
 * A wrapper around {@link CameraDevice.StateCallback} to provide the opened
 * camera synchronously.
 */
@SuppressWarnings("MissingPermission")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CameraConnection extends CameraDevice.StateCallback implements ConnectionOperator {

	private final CountDownLatch countDownLatch = new CountDownLatch(1);
	private final CameraManager manager;
	private final CameraSelector cameraSelector;

	private Characteristics characteristics;
	private CameraDevice camera;
	private Listener listener;

	public CameraConnection(CameraManager manager,
							CameraSelector cameraSelector) {
		this.manager = manager;
		this.cameraSelector = cameraSelector;
	}

	@Override
	public void open(LensPosition lensPosition) {
		String cameraId = cameraSelector.findCameraId(lensPosition);

		try {
			characteristics = new Characteristics(
					manager.getCameraCharacteristics(cameraId)
			);

			manager.openCamera(cameraId,
					this,
					CameraThread
							.getInstance()
							.createHandler()
			);
		} catch (CameraAccessException e) {
			throw new CameraException(e);
		}
	}

	@Override
	public void close() {
		if (camera != null) {
			camera.close();
		}
		if (listener != null) {
			listener.onConnectionClosed();
		}
	}

	@Override
	public void onOpened(@NonNull CameraDevice camera) {
		this.camera = camera;
		countDownLatch.countDown();
	}

	@Override
	public void onDisconnected(@NonNull CameraDevice camera) {
		camera.close();
	}

	@Override
	public void onError(@NonNull CameraDevice camera, int error) {
		camera.close();
	}

	/**
	 * Waits and returns the {@link CameraDevice} synchronously after it has been
	 * obtained.
	 *
	 * @return the requested {@link CameraDevice} to open
	 */
	public CameraDevice getCamera() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// do nothing
		}
		return camera;
	}

	/**
	 * @return characteristics of currently opened camera.
	 * @throws IllegalStateException if camera is not opened yet.
	 */
	public Characteristics getCharacteristics() {
		ensureCharacteristicsAvailable();

		return characteristics;
	}

	private void ensureCharacteristicsAvailable() {
		if (characteristics == null) {
			throw new IllegalStateException("Camera was not opened yet. Characteristics are not available.");
		}
	}

	/**
	 * Sets the listener to be notified when the connection closes.
	 *
	 * @param listener The listener to receive the events.
	 */
	public void setListener(Listener listener) {
		this.listener = listener;
	}

	/**
	 * Notifies the connectivity of the camera.
	 */
	public interface Listener {

		/**
		 * Called when the connection to the camera has been closed.
		 */
		void onConnectionClosed();
	}
}
