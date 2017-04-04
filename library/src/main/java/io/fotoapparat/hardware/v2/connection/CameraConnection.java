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
	private final Characteristics capabilities;
	private CameraDevice camera;

	public CameraConnection(CameraManager manager,
							CameraSelector cameraSelector,
							Characteristics capabilities) {
		this.manager = manager;
		this.cameraSelector = cameraSelector;
		this.capabilities = capabilities;
	}

	@Override
	public void open(LensPosition lensPosition) {
		String cameraId = cameraSelector.findCameraId(lensPosition);

		try {
			capabilities.setCameraId(cameraId);
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
}
