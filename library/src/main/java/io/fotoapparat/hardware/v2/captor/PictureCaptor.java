package io.fotoapparat.hardware.v2.captor;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.photo.Photo;

/**
 *
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PictureCaptor implements PhotoCaptor {

	private final CapturePhotoAction capturePhotoAction = new CapturePhotoAction();

	public PictureCaptor(final CameraDevice camera, final CameraCaptureSession session) {
		CameraThread
				.getInstance()
				.getHandler()
				.post(new Runnable() {
					@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
					@Override
					public void run() {
						try {

							CaptureRequest captureRequest = camera.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)

									.build();

							session.capture(captureRequest, capturePhotoAction, null);
						} catch (CameraAccessException e) {
							// Do nothing
						}
					}
				});
	}

	@Override
	public Photo takePicture() {
		byte[] result = capturePhotoAction.getPhotoBytes();
		return new Photo(
				result,
				0 // fixme
		);
	}
}
