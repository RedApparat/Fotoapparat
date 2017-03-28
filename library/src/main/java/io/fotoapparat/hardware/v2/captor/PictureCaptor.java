package io.fotoapparat.hardware.v2.captor;

import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.media.ImageReader;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Size;
import android.view.Surface;

import java.util.List;

import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.hardware.v2.capabilities.SizeCapability;
import io.fotoapparat.photo.Photo;

/**
 * Takes a picture
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PictureCaptor implements PhotoCaptor {

	private final CapturePhotoAction capturePhotoAction;

	public PictureCaptor(final CameraDevice camera,
						 CameraCharacteristics cameraCharacteristics,
						 final List<Surface> surfaces, final CameraCaptureSession session) {

		SizeCapability sizeCapability = new SizeCapability(cameraCharacteristics);
		Size largestSize = sizeCapability.getLargestSize();

		ImageReader imageReader = ImageReader
				.newInstance(largestSize.getWidth(),
						largestSize.getHeight(),
						ImageFormat.JPEG,
						1
				);
		capturePhotoAction = new CapturePhotoAction(imageReader);

		CameraThread
				.getInstance()
				.getHandler()
				.post(new Runnable() {
					@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
					@Override
					public void run() {
						try {
							CaptureRequest captureRequest = createCaptureRequest(camera, surfaces);
							session.capture(captureRequest, capturePhotoAction, null);
						} catch (CameraAccessException e) {
							// Do nothing
						}
					}
				});
	}

	private CaptureRequest createCaptureRequest(CameraDevice camera,
												List<Surface> surfaces) throws CameraAccessException {
		CaptureRequest.Builder requestBuilder = camera.createCaptureRequest(
				CameraDevice.TEMPLATE_STILL_CAPTURE);

		for (Surface surface : surfaces) {
			requestBuilder.addTarget(surface);
		}
		return requestBuilder.build();
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
