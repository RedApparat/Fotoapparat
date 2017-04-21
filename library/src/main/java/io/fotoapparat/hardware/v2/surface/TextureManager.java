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
import io.fotoapparat.hardware.v2.parameters.ParametersProvider;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.view.TextureListener;

/**
 * Manages the {@link SurfaceTexture} of a {@link TextureView}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class TextureManager
		implements TextureListener.Listener, OrientationManager.Listener, SurfaceOperator {

	private final CountDownLatch surfaceLatch = new CountDownLatch(1);
	private final ParametersProvider parametersProvider;
	private Surface surface;
	private TextureView textureView;
	private int screenOrientation;

	public TextureManager(OrientationManager orientationManager,
						  ParametersProvider parametersProvider) {
		this.parametersProvider = parametersProvider;
		orientationManager.addListener(this);
	}

	private static void correctRotatedDimensions(Matrix matrix,
												 float width,
												 float height,
												 float centerHorizontal,
												 float centerVertical) {
		float rotationCorrectionScaleHeight = height / width;
		float rotationCorrectionScaleWidth = width / height;

		matrix.postScale(
				rotationCorrectionScaleHeight,
				rotationCorrectionScaleWidth,
				centerHorizontal,
				centerVertical
		);
	}

	private static void correctRotation(Matrix matrix,
										int screenOrientation,
										float centerHorizontal,
										float centerVertical) {
		matrix.postRotate(360 - screenOrientation, centerHorizontal, centerVertical);
	}

	@Override
	public void onDisplayOrientationChanged(int orientation) {
		screenOrientation = orientation;
		if (textureView == null) {
			return;
		}
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
		setBufferSize(surfaceTexture);

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

	private void setBufferSize(SurfaceTexture surfaceTexture) {
		Size previewSize = parametersProvider.getPreviewSize();
		surfaceTexture.setDefaultBufferSize(previewSize.width, previewSize.height);
	}

	private void correctOrientation(float width, float height) {
		if (width == 0 || height == 0) {
			return;
		}

		final Matrix matrix = new Matrix();

		float centerHorizontal = width / 2;
		float centerVertical = height / 2;

		if (screenOrientation % 180 == 90) {
			correctRotatedDimensions(matrix, width, height, centerHorizontal, centerVertical);
		}

		correctRotation(matrix, screenOrientation, centerHorizontal, centerVertical);

		new Handler(Looper.getMainLooper())
				.post(new Runnable() {
					@Override
					public void run() {
						textureView.setTransform(matrix);
					}
				});

	}

}
