package io.fotoapparat.hardware.v2.session;

import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Surface;

import io.fotoapparat.hardware.v2.captor.SurfaceReader;

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

	private PreviewSession(CameraDevice camera, SurfaceReader surfaceReader, Surface surface) {
		super(camera, surfaceReader, surface);
		this.camera = camera;
		this.surface = surface;
	}

	/**
	 * Creates a new instance of this session.
	 */
	public static PreviewSession create(CameraDevice camera, SurfaceReader surfaceReader, SurfaceTexture surfaceTexture) {
		return new PreviewSession(camera, surfaceReader, new Surface(surfaceTexture));
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
