package io.fotoapparat.hardware.v2.captor;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.concurrent.CountDownLatch;

import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.hardware.v2.connection.CameraConnection;
import io.fotoapparat.photo.Photo;

/**
 * Responsible to capture a picture.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PictureCaptor extends CameraCaptureSession.CaptureCallback {

	private final CountDownLatch countDownLatch = new CountDownLatch(1);
	private final SurfaceReader surfaceReader;
	private final CameraConnection cameraConnection;
	private byte[] photoBytes;

	public PictureCaptor(SurfaceReader surfaceReader, CameraConnection cameraConnection) {
		this.surfaceReader = surfaceReader;
		this.cameraConnection = cameraConnection;
	}

	@Override
	public void onCaptureCompleted(@NonNull CameraCaptureSession session,
								   @NonNull CaptureRequest request,
								   @NonNull TotalCaptureResult result) {
		super.onCaptureCompleted(session, request, result);

		this.photoBytes = surfaceReader.getPhotoBytes();
		countDownLatch.countDown();
	}

	@Override
	public void onCaptureFailed(@NonNull CameraCaptureSession session,
								@NonNull CaptureRequest request,
								@NonNull CaptureFailure failure) {
		super.onCaptureFailed(session, request, failure);
		// TODO: 27.03.17 support failure
	}

	/**
	 * Captures photo synchronously.
	 *
	 * @param captureSession the currently opened capture session of the camera
	 * @return a new Photo
	 * @throws CameraAccessException if the camera device is no longer connected or has encountered
	 *                               a fatal error
	 */
	public Photo takePhoto(CameraCaptureSession captureSession) throws CameraAccessException {
		capture(captureSession);

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// do nothing
		}

		return new Photo(
				photoBytes,
				0 // fixme
		);
	}

	private void capture(CameraCaptureSession session) throws CameraAccessException {
		CaptureRequest captureRequest = createCaptureRequest();

//		session.stopRepeating();
		session.capture(captureRequest,
				this,
				CameraThread
						.getInstance()
						.createHandler()
		);
	}

	private CaptureRequest createCaptureRequest() throws CameraAccessException {
		CaptureRequest.Builder requestBuilder = cameraConnection
				.getCamera()
				.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);

		requestBuilder.addTarget(surfaceReader.getSurface());
		requestBuilder.set(CaptureRequest.JPEG_ORIENTATION, 0); // TODO: 02/04/17

		return requestBuilder.build();
	}
}
