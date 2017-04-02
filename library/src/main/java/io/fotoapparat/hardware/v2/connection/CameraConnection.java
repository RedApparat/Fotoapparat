package io.fotoapparat.hardware.v2.connection;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.concurrent.CountDownLatch;

import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.hardware.v2.capabilities.CameraCapabilities;
import io.fotoapparat.hardware.v2.captor.PictureCaptor;

/**
 * A wrapper around {@link CameraDevice.StateCallback} to provide the opened
 * camera synchronously.
 */
@SuppressWarnings("MissingPermission")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CameraConnection extends CameraDevice.StateCallback {

	private final CountDownLatch countDownLatch = new CountDownLatch(1);
	private final CameraManager manager;
	private final CameraCapabilities capabilities;
	private CameraDevice camera;

	public CameraConnection(CameraManager manager, CameraCapabilities capabilities) {
		this.manager = manager;
		this.capabilities = capabilities;
	}

	/**
	 * Open a connection to a camera with the given ID.
	 *
	 * @param cameraId the camera id
	 * @throws CameraAccessException if the camera is disabled by device policy, has been
	 *                               disconnected, or is being used by a higher-priority camera API
	 *                               client.
	 */
	public void openById(String cameraId) throws CameraAccessException {
		capabilities.setCameraId(cameraId);
		manager.openCamera(cameraId,
				this,
				CameraThread
						.getInstance()
						.createHandler()
		);
	}

	/**
	 * Closes a connection if a camera has opened.
	 */
	public void close() {
		if (camera != null) {
			camera.close();
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
	 * @return the requested {@link CameraDevice} to openById
	 */
	public CameraDevice getCamera() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// do nothing
		}
		return camera;
	}
}
