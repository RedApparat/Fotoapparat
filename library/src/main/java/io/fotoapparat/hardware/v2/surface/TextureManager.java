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
import io.fotoapparat.hardware.v2.parameters.SizeProvider;
import io.fotoapparat.view.TextureListener;

import static java.lang.Math.round;

/**
 * Manages the {@link SurfaceTexture} of a {@link TextureView}.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class TextureManager
		implements TextureListener.Listener, OrientationManager.Listener, SurfaceOperator {

	private final CountDownLatch surfaceLatch = new CountDownLatch(1);
	private final OrientationManager orientationManager;
	private final SizeProvider sizeProvider;
	private Surface surface;
	private TextureView textureView;
	private SurfaceTexture surfaceTexture;

	public TextureManager(OrientationManager orientationManager,
						  SizeProvider sizeProvider) {
		this.orientationManager = orientationManager;
		this.sizeProvider = sizeProvider;
		orientationManager.setListener(this);
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
		surfaceTexture = textureView.getSurfaceTexture();

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

	private void correctOrientation(float width, float height) {
		if (width == 0 || height == 0) {
			return;
		}
		int screenOrientation = orientationManager.getScreenOrientation();
		float aspectRatio = sizeProvider.getStillCaptureAspectRatio();

		final Matrix matrix = new Matrix();

		float centerHorizontal = width / 2;
		float centerVertical = height / 2;

		float previewAspectRatio = width / height;

		if (screenOrientation % 180 == 90) {
			previewAspectRatio = height / width;

			correctRotatedDimensions(matrix, width, height, centerHorizontal, centerVertical);
		}

		correctRotation(matrix, screenOrientation, centerHorizontal, centerVertical);

		float horizontalScale = 1;
		float verticalScale = 1;

		if (width < height) {
			verticalScale = 1 * aspectRatio * previewAspectRatio;
		} else {
			horizontalScale = 1 * aspectRatio * previewAspectRatio;
		}

		matrix.postScale(
				horizontalScale,
				verticalScale,
				centerHorizontal,
				centerVertical
		);

		correctBufferSize(width, height, horizontalScale, verticalScale);

		new Handler(Looper.getMainLooper())
				.post(new Runnable() {
					@Override
					public void run() {
						textureView.setTransform(matrix);
					}
				});
	}

	private void correctBufferSize(float width,
								   float height,
								   float horizontalScale,
								   float verticalScale) {
		surfaceTexture.setDefaultBufferSize(
				round(width * horizontalScale),
				round(height * verticalScale)
		);
	}

}
