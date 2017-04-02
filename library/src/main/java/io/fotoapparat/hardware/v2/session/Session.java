package io.fotoapparat.hardware.v2.session;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Surface;

import java.util.Arrays;

import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.hardware.v2.captor.SurfaceReader;

/**
 * Basic wrapper around the internal {@link CameraCaptureSession}
 * for a particular {@link CameraDevice}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Session {

	private final CaptureSessionAction captureSessionAction = new CaptureSessionAction();
	private final Surface surface;

	public Session(CameraDevice camera, SurfaceReader surfaceReader) {
		this(camera, surfaceReader, null);
	}

	Session(final CameraDevice camera,
			final SurfaceReader surfaceReader,
			final Surface surface) {
		this.surface = surface;

		try {
			camera.createCaptureSession(
					Arrays.asList(surface, surfaceReader.getSurface()),
					captureSessionAction,
					CameraThread
							.getInstance()
							.createHandler()
			);
		} catch (CameraAccessException e) {
			// Do nothing
		}
	}

	Surface getSurface() {
		return surface;
	}

	public CameraCaptureSession getCaptureSession() {
		return captureSessionAction.getCaptureSession();
	}
}
