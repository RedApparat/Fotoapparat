package io.fotoapparat.hardware.v2.captor;

import android.hardware.camera2.CameraAccessException;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.operators.CaptureOperator;
import io.fotoapparat.hardware.v2.captor.routine.CapturingRoutine;
import io.fotoapparat.hardware.v2.orientation.OrientationManager;
import io.fotoapparat.hardware.v2.session.Session;
import io.fotoapparat.hardware.v2.session.SessionManager;
import io.fotoapparat.photo.Photo;

/**
 * Responsible to capture a picture.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PictureCaptor implements CaptureOperator {

	private final SessionManager sessionManager;
	private final CapturingRoutine capturingRoutine;
	private final OrientationManager orientationManager;

	public PictureCaptor(SessionManager sessionManager,
						 CapturingRoutine capturingRoutine,
						 OrientationManager orientationManager) {
		this.sessionManager = sessionManager;
		this.capturingRoutine = capturingRoutine;
		this.orientationManager = orientationManager;
	}

	/**
	 * Captures photo synchronously.
	 *
	 * @return a new Photo
	 */
	@Override
	public Photo takePicture() {
		Session captureSession = sessionManager.getCaptureSession();
		Integer sensorOrientation = orientationManager.getSensorOrientation();
		byte[] photoBytes;

		try {
			photoBytes = capturingRoutine.capturePicture(captureSession, sensorOrientation);
		} catch (CameraAccessException e) {
			throw new CameraException(e);
		}

		return new Photo(
				photoBytes,
				0
		);
	}

}
