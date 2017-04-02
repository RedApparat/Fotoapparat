package io.fotoapparat.hardware.v2.session;

import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Surface;

/**
 * Wrapper around the internal {@link android.hardware.camera2.CameraCaptureSession}
 * for a particular {@link CameraDevice}.
 * <p>
 * It facilitates a {@link SurfaceTexture} to preview the results of the {@link CameraDevice}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PreviewSession extends Session implements PreviewOperator {

	private final CameraDevice camera;
	private final Surface surface;

	public PreviewSession(CameraDevice camera, CameraCharacteristics capabilities, SurfaceTexture surfaceTexture) {
		super(camera, capabilities, new Surface(surfaceTexture));
		this.camera = camera;
		this.surface = new Surface(surfaceTexture);
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	public void startPreview() {
		try {
			CaptureRequest.Builder captureRequest = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
			captureRequest.addTarget(surface);
			getCaptureSession().setRepeatingRequest(captureRequest.build(), null, null);
		} catch (CameraAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stopPreview() {
		// TODO: 25/03/17
	}
}
