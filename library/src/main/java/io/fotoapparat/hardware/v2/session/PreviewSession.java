package io.fotoapparat.hardware.v2.session;

import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Surface;

import java.util.Collections;
import java.util.List;

import io.fotoapparat.hardware.CameraException;
import io.fotoapparat.hardware.operators.PreviewOperator;

/**
 * Wrapper around the internal {@link android.hardware.camera2.CameraCaptureSession}
 * for a particular {@link CameraDevice}.
 * <p>
 * It facilitates a {@link SurfaceTexture} to preview the results of the {@link CameraDevice}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PreviewSession extends Session implements PreviewOperator {

	private final CaptureRequest captureRequest;

	PreviewSession(CameraDevice camera,
				   CaptureRequest captureRequest,
				   List<Surface> surfaces) {
		super(camera, Collections.unmodifiableList(surfaces));
		this.captureRequest = captureRequest;
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	public void startPreview() {
		try {
			getCaptureSession().setRepeatingRequest(
					captureRequest,
					null,
					null
			);
		} catch (CameraAccessException e) {
			throw new CameraException(e);
		}
	}

	@Override
	public void stopPreview() {
		// TODO: 25/03/17
	}
}
