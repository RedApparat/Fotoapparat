package io.fotoapparat.hardware.v2.captor;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.operators.CaptureOperator;
import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.hardware.v2.capabilities.Characteristics;
import io.fotoapparat.hardware.v2.parameters.CaptureRequestFactory;
import io.fotoapparat.hardware.v2.session.Session;
import io.fotoapparat.hardware.v2.session.SessionManager;
import io.fotoapparat.hardware.v2.surface.StillSurfaceReader;
import io.fotoapparat.photo.Photo;

/**
 * Performs a picture capturing routine.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CapturingRoutine implements CaptureOperator {

	private final CaptureRequestFactory captureRequestFactory;
	private final StillSurfaceReader surfaceReader;
	private final SessionManager sessionManager;
	private final Characteristics characteristics;

	public CapturingRoutine(CaptureRequestFactory captureRequestFactory,
							StillSurfaceReader surfaceReader,
							SessionManager sessionManager,
							Characteristics characteristics) {
		this.captureRequestFactory = captureRequestFactory;
		this.surfaceReader = surfaceReader;
		this.sessionManager = sessionManager;
		this.characteristics = characteristics;
	}

	@Override
	public Photo takePicture() {
		byte[] photoBytes;

		try {
			photoBytes = capturePicture();
		} catch (CameraAccessException e) {
			throw new CameraException(e);
		}

		return new Photo(photoBytes, 0);
	}

	private byte[] capturePicture() throws CameraAccessException {
		Session session = sessionManager.getCaptureSession();
		Integer sensorOrientation = characteristics.getSensorOrientation();

		Stage stage = Stage.UNFOCUSED;
		while (stage != Stage.CAPTURE_COMPLETED) {
			stage = triggerStagedCaptureSession(session, stage, sensorOrientation);
		}
		return surfaceReader.getPhotoBytes();
	}

	private Stage triggerStagedCaptureSession(Session session,
											  Stage stage,
											  Integer sensorOrientation) throws CameraAccessException {

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

		return stageCallback.getCaptureStage();
	}

}
