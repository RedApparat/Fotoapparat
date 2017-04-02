package io.fotoapparat.hardware.v2.session;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
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
	private final CameraCharacteristics cameraCharacteristics;
	private final List<Surface> surfaces;

	public Session(CameraDevice camera, CameraCharacteristics cameraCharacteristics) {
		this(camera, cameraCharacteristics, Collections.<Surface>emptyList());
	}

	Session(CameraDevice camera, CameraCharacteristics cameraCharacteristics, Surface surface) {
		this(camera, cameraCharacteristics, Collections.singletonList(surface));
	}

	private Session(final CameraDevice camera,
					CameraCharacteristics cameraCharacteristics,
					final List<Surface> surfaces) {
		this.camera = camera;
		this.cameraCharacteristics = cameraCharacteristics;
		this.surfaces = surfaces;

		try {
			camera.createCaptureSession(
					surfaces,
					captureSessionAction,
					CameraThread
							.getInstance()
							.getHandler()
			);
		} catch (CameraAccessException e) {
			// Do nothing
		}
	}

	CameraCaptureSession getCaptureSession() {
		return captureSessionAction.getCaptureSession();
	}

	@Override
	public Photo takePicture() {

		PictureCaptor pictureCaptor = new PictureCaptor(
				camera,
				cameraCharacteristics,
				surfaces,
				getCaptureSession()
		);
		return pictureCaptor.takePicture();
	}
}
