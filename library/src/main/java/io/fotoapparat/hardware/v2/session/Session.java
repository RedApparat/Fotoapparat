package io.fotoapparat.hardware.v2.session;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Surface;

import java.util.Collections;
import java.util.List;

import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.hardware.v2.captor.PhotoCaptor;
import io.fotoapparat.hardware.v2.captor.PictureCaptor;
import io.fotoapparat.photo.Photo;

/**
 * Basic wrapper around the internal {@link CameraCaptureSession}
 * for a particular {@link CameraDevice}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Session implements PhotoCaptor {

	private final CaptureSessionAction captureSessionAction = new CaptureSessionAction();
	private final CameraDevice camera;

	public Session(CameraDevice camera) {
		this(camera, Collections.<Surface>emptyList());
	}

	Session(CameraDevice camera, Surface surface) {
		this(camera, Collections.singletonList(surface));
	}

	private Session(final CameraDevice camera, final List<Surface> surfaces) {
		this.camera = camera;
		CameraThread
				.getInstance()
				.getHandler()
				.post(new Runnable() {
					@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
					@Override
					public void run() {
						try {
							camera.createCaptureSession(
									surfaces,
									captureSessionAction,
									null
							);
						} catch (CameraAccessException e) {
							// Do nothing
						}
					}
				});
	}

	CameraCaptureSession getCaptureSession() {
		return captureSessionAction.getCaptureSession();
	}

	@Override
	public Photo takePicture() {
		PictureCaptor pictureCaptor = new PictureCaptor(camera, getCaptureSession());

		return pictureCaptor.takePicture();
	}
}
