package io.fotoapparat.hardware.v2.surface;

import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.view.Surface;
import android.view.TextureView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import io.fotoapparat.hardware.operators.SurfaceOperator;
import io.fotoapparat.hardware.v2.orientation.OrientationManager;
import io.fotoapparat.parameter.Size;
import io.fotoapparat.util.CompareSizesByArea;
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

	private static Size choosePreviewSize(Size[] availableSizes,
										  Size textureSize,
										  Size size) {

		int maxPreviewWidth = MAX_PREVIEW_WIDTH;
		int maxPreviewHeight = MAX_PREVIEW_HEIGHT;

		if (textureSize.width < maxPreviewWidth) {
			maxPreviewWidth = textureSize.width;
		}
		if (textureSize.height < maxPreviewHeight) {
			maxPreviewHeight = textureSize.height;
		}

		Size maxSize = new Size(maxPreviewWidth, maxPreviewHeight);

		// Collect the supported resolutions that are at least as big as the preview Surface
		List<Size> bigEnough = new ArrayList<>();
		// Collect the supported resolutions that are smaller than the preview Surface
		List<Size> notBigEnough = new ArrayList<>();

		for (Size availableSize : availableSizes) {

			if (isFirstSmaller(availableSize, maxSize) && sameRatio(availableSize, size)) {

				if (isFirstSmaller(maxSize, availableSize)) {
					bigEnough.add(availableSize);
				} else {
					notBigEnough.add(availableSize);
				}
			}
		}

		// Pick the smallest of those big enough. If there is no one big enough, pick the
		// largest of those not big enough.
		if (bigEnough.size() > 0) {
			return Collections.min(bigEnough, new CompareSizesByArea());
		} else if (notBigEnough.size() > 0) {
			return Collections.max(notBigEnough, new CompareSizesByArea());
		} else {
			return availableSizes[0];
		}
	}

	private static boolean isFirstSmaller(Size expectedSmallerSize, Size expectedBiggerSize) {
		return expectedSmallerSize.width <= expectedBiggerSize.width && expectedSmallerSize.height <= expectedBiggerSize.height;
	}

	private static boolean sameRatio(Size size1, Size size2) {
		return size2.height == size2.width * size1.height / size1.width;
	}

	/**
	 * Max preview width that is guaranteed by Camera2 API
	 */
	private static final int MAX_PREVIEW_WIDTH = 1920;

	/**
	 * Max preview height that is guaranteed by Camera2 API
	 */
	private static final int MAX_PREVIEW_HEIGHT = 1080;

}
