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
import io.fotoapparat.hardware.v2.PhotoCaptor;
import io.fotoapparat.photo.Photo;

/**
 * Basic wrapper around the internal {@link CameraCaptureSession}
 * for a particular {@link CameraDevice}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Session implements PhotoCaptor {

	private final CaptureSessionAction captureSessionAction = new CaptureSessionAction();

	public Session(CameraDevice camera) {
		this(camera, Collections.<Surface>emptyList());
	}

	Session(CameraDevice camera, Surface surface) {
		this(camera, Collections.singletonList(surface));
	}

	private Session(final CameraDevice camera, final List<Surface> surfaces) {
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

	CameraCaptureSession getSession() {
		return captureSessionAction.getSession();
	}

	@Override
	public Photo takePicture() {
		// TODO: 25/03/17
		return null;
	}
}
