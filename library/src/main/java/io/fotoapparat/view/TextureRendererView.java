package io.fotoapparat.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.TextureView;
import android.widget.FrameLayout;

import java.util.concurrent.CountDownLatch;

import io.fotoapparat.hardware.CameraDevice;

/**
 * Uses {@link android.view.TextureView} as an output for camera.
 */
class TextureRendererView extends FrameLayout implements CameraRenderer {

	private final CountDownLatch textureLatch = new CountDownLatch(1);

	private SurfaceTexture surfaceTexture;

	public TextureRendererView(@NonNull Context context) {
		super(context);

		init();
	}

	public TextureRendererView(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);

		init();
	}

	public TextureRendererView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		init();
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public TextureRendererView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);

		init();
	}

	private void init() {
		TextureView textureView = new TextureView(getContext());

		addView(textureView);
	}

	@Override
	public void attachCamera(CameraDevice camera) {
		try {
			textureLatch.await();
		} catch (InterruptedException e) {
			// Do nothing
		}

		camera.setDisplaySurface(surfaceTexture);
	}

	private class TextureListener implements TextureView.SurfaceTextureListener {

		@Override
		public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
			surfaceTexture = surface;
			textureLatch.countDown();
		}

		@Override
		public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
			// Do nothing
		}

		@Override
		public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
			// Do nothing

			return true;
		}

		@Override
		public void onSurfaceTextureUpdated(SurfaceTexture surface) {
			// Do nothing
		}
	}

}
