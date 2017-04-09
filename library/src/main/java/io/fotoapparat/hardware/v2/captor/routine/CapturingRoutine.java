package io.fotoapparat.hardware.v2.captor.routine;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.hardware.v2.parameters.CaptureRequestFactory;
import io.fotoapparat.hardware.v2.session.Session;
import io.fotoapparat.hardware.v2.surface.SurfaceReader;

/**
 * Performs a picture capturing routing.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CapturingRoutine {

	private final CaptureRequestFactory captureRequestFactory;
	private final SurfaceReader surfaceReader;

	public CapturingRoutine(CaptureRequestFactory captureRequestFactory,
							SurfaceReader surfaceReader) {
		this.captureRequestFactory = captureRequestFactory;
		this.surfaceReader = surfaceReader;
	}

	/**
	 * Will start a photo capturing routing which will block the current thread until the photo has
	 * been captured.
	 *
	 * @param session           The current preview or normal {@link Session}.
	 * @param sensorOrientation The orientation of the moment of the capture.
	 * @return The Image as byte array.
	 * @throws CameraAccessException If the camera device has been disconnected.
	 */
	public byte[] capturePicture(Session session,
								 Integer sensorOrientation) throws CameraAccessException {

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
