package io.fotoapparat.hardware.v2.session;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.Surface;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import io.fotoapparat.hardware.v2.CameraThread;
import io.fotoapparat.hardware.v2.captor.SurfaceReader;

/**
 * Basic wrapper around the internal {@link CameraCaptureSession}
 * for a {@link CameraDevice} to provide the opened session synchronously.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Session extends CameraCaptureSession.StateCallback {

	private final CountDownLatch sessionLatch = new CountDownLatch(1);
	private final CameraDevice camera;
	private final SurfaceReader surfaceReader;
	private final Surface surface;
	private CameraCaptureSession session;

	public Session(CameraDevice camera, SurfaceReader surfaceReader) {
		this(camera, surfaceReader, null);
	}

	Session(final CameraDevice camera,
			final SurfaceReader surfaceReader,
			final Surface surface) {
		this.camera = camera;
		this.surfaceReader = surfaceReader;
		this.surface = surface;
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
					Arrays.asList(surface, surfaceReader.getSurface()),
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
