package io.fotoapparat.hardware.v2.surface;

import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.view.Surface;
import android.view.TextureView;

import java.util.concurrent.CountDownLatch;

import io.fotoapparat.hardware.operators.SurfaceOperator;
import io.fotoapparat.hardware.v2.orientation.OrientationManager;
import io.fotoapparat.view.TextureListener;

/**
 * Manages the {@link SurfaceTexture} of a {@link TextureView}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class TextureManager
		implements TextureListener.Listener, OrientationManager.Listener, SurfaceOperator {

	private final CountDownLatch surfaceLatch = new CountDownLatch(1);
	private final OrientationManager orientationManager;
	private Surface surface;
	private TextureView textureView;

	public TextureManager(OrientationManager orientationManager) {
		this.orientationManager = orientationManager;
		orientationManager.setListener(this);
	}

	private static float[] getDst(int orientation, int width, int height) {
		if (orientation == 90) {
			return new float[]{
					0.f, height, // top left
					0.f, 0.f, // top right
					width, height, // bottom left
					width, 0.f, // bottom right
			};
		}
		return new float[]{
				width, 0.f, // top left
				width, height, // top right
				0.f, 0.f, // bottom left
				0.f, height, // bottom right
		};
	}

	/**
	 * Notifies that the display orientation has changed.
	 */
	@Override
	public void onDisplayOrientationChanged() {
		correctOrientation(textureView.getWidth(), textureView.getHeight());
	}

	@Override
	public void setDisplaySurface(Object displaySurface) {
		if (!(displaySurface instanceof TextureView)) {
			throw new IllegalArgumentException("Unsupported display surface: " + displaySurface);
		}

		textureView = (TextureView) displaySurface;
		SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();

		if (surfaceTexture != null) {
			onSurfaceAvailable(surfaceTexture);
			correctOrientation(textureView.getWidth(), textureView.getHeight());
			surfaceLatch.countDown();
		}
		textureView.setSurfaceTextureListener(new TextureListener(this));

	}

	@Override
	public void onSurfaceAvailable(SurfaceTexture surfaceTexture) {
		this.surface = new Surface(surfaceTexture);
		surfaceLatch.countDown();
	}

	@Override
	public void onTextureSizeChanged(int width, int height) {
		correctOrientation(width, height);
	}

	/**
	 * Returns the {@link Surface} when it becomes available.
	 *
	 * @return the surface of the view.
	 */
	public Surface getSurface() {
		try {
			surfaceLatch.await();
		} catch (InterruptedException e) {
			// Do nothing
		}
		return surface;
	}

	private void correctOrientation(int width, int height) {
		final Matrix matrix = new Matrix();
		int screenOrientation = orientationManager.getScreenOrientation();

		if (screenOrientation % 180 == 90) {
			float[] src = {
					0.f, 0.f, // top left
					width, 0.f, // top right
					0.f, height, // bottom left
					width, height, // bottom right
			};

			float[] dst = getDst(screenOrientation, width, height);

			matrix.setPolyToPoly(src, 0, dst, 0, 4);
		}
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				textureView.setTransform(matrix);
			}
		});
	}

}
