package io.fotoapparat.hardware.v2.session;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.Surface;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import io.fotoapparat.hardware.v2.CameraThread;

/**
 * Basic wrapper around the internal {@link CameraCaptureSession}
 * for a {@link CameraDevice} to provide the opened session synchronously.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Session extends CameraCaptureSession.StateCallback {

	private final CountDownLatch sessionLatch = new CountDownLatch(1);
	private final CameraDevice camera;
	private final List<Surface> outputSurfaces;
	private CameraCaptureSession session;

	Session(CameraDevice camera, Surface viewSurface, Surface captureSurface) {
		this(camera, Arrays.asList(viewSurface, captureSurface));
	}

	Session(CameraDevice camera, Surface captureSurface) {
		this(camera, Collections.singletonList(captureSurface));
	}

	private Session(CameraDevice camera, List<Surface> outputSurfaces) {
		this.camera = camera;
		this.outputSurfaces = outputSurfaces;
	}

	/**
	 * Waits and returns the {@link CameraCaptureSession} synchronously after it has been
	 * obtained.
	 *
	 * @return the requested {@link CameraCaptureSession} to open
	 */
	public CameraCaptureSession getCaptureSession() {
		if (session == null) {
			createCaptureSession();
		}
		return session;
	}

	/**
	 * Closes the {@link CameraCaptureSession} asynchronously.
	 */
	public void close() {
		if (session != null) {
			session.close();
		}
	}

	@Override
	public void onConfigured(@NonNull CameraCaptureSession session) {
		this.session = session;
		sessionLatch.countDown();
	}

	@Override
	public void onConfigureFailed(@NonNull CameraCaptureSession session) {
		session.close();
	}

	@Override
	public void onClosed(@NonNull CameraCaptureSession session) {
		super.onClosed(session);
	}

	private void createCaptureSession() {
		try {
			camera.createCaptureSession(
					outputSurfaces,
					this,
					CameraThread
							.getInstance()
							.createHandler()
			);
			sessionLatch.await();
		} catch (CameraAccessException | InterruptedException e) {
			// Do nothing
		}
	}
}
