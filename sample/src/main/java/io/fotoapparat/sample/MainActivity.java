package io.fotoapparat.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.log.LogcatLogger;
import io.fotoapparat.photo.BitmapPhoto;
import io.fotoapparat.preview.Frame;
import io.fotoapparat.preview.FrameProcessor;
import io.fotoapparat.result.PendingResult;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.view.CameraView;

import static io.fotoapparat.parameter.selector.AspectRatioSelectors.standardRatio;
import static io.fotoapparat.parameter.selector.FlashSelectors.autoFlash;
import static io.fotoapparat.parameter.selector.FlashSelectors.autoRedEye;
import static io.fotoapparat.parameter.selector.FlashSelectors.torch;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.autoFocus;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.continuousFocus;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.fixed;
import static io.fotoapparat.parameter.selector.LensPositionSelectors.back;
import static io.fotoapparat.parameter.selector.Selectors.firstAvailable;
import static io.fotoapparat.parameter.selector.SizeSelectors.biggestSize;

public class MainActivity extends AppCompatActivity {

	private Fotoapparat fotoapparat;
	private final PermissionsDelegate permissionsDelegate = new PermissionsDelegate(this);
	private boolean hasCameraPermission;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		CameraView cameraView = (CameraView) findViewById(R.id.camera_view);

		hasCameraPermission = permissionsDelegate.hasCameraPermission();

		if (!hasCameraPermission) {
			permissionsDelegate.requestCameraPermission();
		} else {
			cameraView.setVisibility(View.VISIBLE);
		}

		fotoapparat = Fotoapparat
				.with(this)
				.into(cameraView)
				.photoSize(standardRatio(biggestSize()))
				.lensPosition(back())
				.focusMode(firstAvailable(
						continuousFocus(),
						autoFocus(),
						fixed()
				))
				.flash(firstAvailable(
						autoRedEye(),
						autoFlash(),
						torch()
				))
				.frameProcessor(new SampleFrameProcessor())
				.logger(new LogcatLogger())
				.build();

		cameraView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				takePicture();
			}
		});
	}

	private void takePicture() {
		PhotoResult photoResult = fotoapparat.takePicture();

		photoResult.saveToFile(new File(
				getExternalFilesDir("photos"),
				"photo.jpg"
		));

		photoResult
				.toBitmap()
				.whenAvailable(new PendingResult.Callback<BitmapPhoto>() {
					@Override
					public void onResult(BitmapPhoto result) {
						ImageView imageView = (ImageView) findViewById(R.id.result);

						imageView.setImageBitmap(result.bitmap);
						imageView.setRotation(-result.rotationDegrees);
					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (hasCameraPermission) {
			fotoapparat.start();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (hasCameraPermission) {
			fotoapparat.stop();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String[] permissions,
										   @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		permissionsDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	private class SampleFrameProcessor implements FrameProcessor {

		@Override
		public void processFrame(Frame frame) {
			// Do nothing
		}

	}

}
