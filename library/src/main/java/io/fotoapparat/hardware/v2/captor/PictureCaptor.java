package io.fotoapparat.hardware.v2.captor;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.operators.CaptureOperator;
import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.hardware.v2.orientation.OrientationManager;
import io.fotoapparat.hardware.v2.session.Session;
import io.fotoapparat.hardware.v2.session.SessionManager;
import io.fotoapparat.hardware.v2.surface.SurfaceReader;
import io.fotoapparat.photo.Photo;

/**
 * Responsible to capture a picture.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PictureCaptor implements CaptureOperator {

	private final SurfaceReader surfaceReader;
	private final SessionManager sessionManager;
	private final CaptureRequestFactory captureRequestFactory;
	private final OrientationManager orientationManager;

	public PictureCaptor(SurfaceReader surfaceReader,
						 SessionManager sessionManager,
						 CaptureRequestFactory captureRequestFactory,
						 OrientationManager orientationManager) {
		this.surfaceReader = surfaceReader;
		this.sessionManager = sessionManager;
		this.captureRequestFactory = captureRequestFactory;
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
		try {
			captureRoutine(captureSession, sensorOrientation);
		} catch (CameraAccessException e) {
			throw new CameraException(e);
		}

		return new Photo(
				surfaceReader.getPhotoBytes(),
				sensorOrientation
		);
	}

	private void captureRoutine(Session session,
								Integer sensorOrientation) throws CameraAccessException {

		Stage stage = Stage.UNFOCUSED;
		while (stage != Stage.CAPTURE_COMPLETED) {
			stage = triggerCapture(session, sensorOrientation, stage);
		}
	}

	private Stage triggerCapture(Session session,
								 Integer sensorOrientation,
								 Stage stage) throws CameraAccessException {

		Log.wtf("PictureCaptor", "triggerCapture " + stage);

		StageCallback stageCallback;
		CaptureRequest captureRequest;

		switch (stage) {
			case UNFOCUSED:
				captureRequest = captureRequestFactory.createLockRequest();
				stageCallback = new LockFocusCallback();
				break;
			case PRECAPTURE:
				captureRequest = captureRequestFactory.createPrecaptureRequest();
				stageCallback = new PrecaptureCallback();
				break;
			case CAPTURE:
				captureRequest = captureRequestFactory.createCaptureRequest(sensorOrientation);
				stageCallback = new CaptureCallback(session);
				session.getCaptureSession().stopRepeating(); // TODO: 05.04.17 need?
				break;
			default:
				throw new IllegalStateException("Unsupported stage: " + stage);
		}


		session.getCaptureSession()
				.capture(
						captureRequest,
						stageCallback,
						CameraThread
								.getInstance()
								.createHandler()
				);

		return stageCallback.onStageAcquired();
	}

}
